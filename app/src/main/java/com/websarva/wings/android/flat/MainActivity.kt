package com.websarva.wings.android.flat

import android.Manifest
import android.annotation.TargetApi
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.websarva.wings.android.flat.other.PermissionConstants.REQUEST_CODE_LOCATION
import com.websarva.wings.android.flat.other.PermissionConstants.REQUEST_CODE_LOCATION_BACKGROUND

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isBeaconCompatible()
        requestPermission()
        bluetoothOnRequest()
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        val serviceIntent = Intent(this, BeaconDetectionService::class.java)
        startForegroundService(serviceIntent)


        // 全体の画面遷移を制御
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_navigation)
            .setupWithNavController(navController)

        // Toolbarに戻るボタンを表示し、グラフを渡して遷移を管理する
        setupActionBarWithNavController(navController, AppBarConfiguration(navController.graph))

        // DestinationによってBottomNavigationを消したり、Toolbarを書き換えたりする
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.addFriendFragment) {
                findViewById<Toolbar>(R.id.toolbar).also {
                    it.visibility = View.VISIBLE
                    it.title = getString(R.string.title_add_friend)
                    it.navigationIcon = AppCompatResources.getDrawable(this, R.drawable.ic_delete)
                }
                findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.GONE
            } else {
                findViewById<Toolbar>(R.id.toolbar).visibility = View.GONE
                findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
            }
        }
    }
    // Toolbarの戻るボタンを機能させる
    override fun onSupportNavigateUp() = findNavController(R.id.navHost).navigateUp()

    // BluetoothLE対応端末かの判別
    private fun isBeaconCompatible(){
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_LONG).show()
            finish()
        }
    }

    // Bluetoothがoffになっているときの処理
    private val bluetoothAdapter: BluetoothAdapter? by lazy(LazyThreadSafetyMode.NONE) {
        val bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }
    private val BluetoothAdapter.isDisabled: Boolean
        get() = !isEnabled
    private fun bluetoothOnRequest(){
        if (bluetoothAdapter?.isDisabled == true) {
            Toast.makeText(this, "Bluetoothをオンにします...", Toast.LENGTH_SHORT).show()
            bluetoothAdapter?.enable()
        }
    }

    // 位置情報に関するPermission
    private fun requestPermission() {
        when {
            Build.VERSION.SDK_INT < Build.VERSION_CODES.Q -> {
                checkLocationPermission()
            }
            Build.VERSION.SDK_INT < Build.VERSION_CODES.R -> {
                checkLocationPermissionQ()
            }
            else -> {
                checkLocationPermission()
                checkBackgroundLocationPermissionR()
            }
        }
    }

    private fun checkLocationPermission() {
        if (!checkSinglePermission(Manifest.permission.ACCESS_FINE_LOCATION) ||
            !checkSinglePermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            val permList = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            requestPermissions(permList, REQUEST_CODE_LOCATION)
        }
    }

    @TargetApi(Build.VERSION_CODES.Q)
    private fun checkLocationPermissionQ() {
        if (checkSinglePermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkSinglePermission(Manifest.permission.ACCESS_COARSE_LOCATION) &&
            checkSinglePermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) return
        val permList = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        requestPermissions(permList, REQUEST_CODE_LOCATION)
    }


    @TargetApi(Build.VERSION_CODES.R)
    private fun checkBackgroundLocationPermissionR() {
        if (checkSinglePermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) return
        AlertDialog.Builder(this)
            .setTitle("")
            .setMessage(R.string.permission_background_location_message)
            .setPositiveButton(R.string.permission_background_location_positive) { _,_ ->
                requestPermissions(arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION), REQUEST_CODE_LOCATION_BACKGROUND)
            }
            .setNegativeButton(R.string.permission_background_location_negative) { dialog,_ ->
                //TODO: 設定を拒否された場合どうするか
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun checkSinglePermission(permission: String) : Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 許可された場合
                //TODO: Rの場合とそれ以外で分け、画面遷移など
            }
            else {
                // 許可されなかった場合
            }
        }
        if (requestCode == REQUEST_CODE_LOCATION_BACKGROUND) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 許可された場合
            }
            else {
                // 許可されなかった場合
            }
        }
    }
}