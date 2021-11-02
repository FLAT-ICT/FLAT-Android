package com.websarva.wings.android.flat

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.websarva.wings.android.flat.other.Permissions
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

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
            if(destination.id == R.id.addFriendFragment) {
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

    private fun requestPermissions() {
        if (Permissions.locationPermission(this)) {
            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "このアプリを使うには位置情報へのアクセスを許可する必要があります",
                0,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "このアプリを使うには位置情報へのアクセスを許可する必要があります",
                0,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }
}