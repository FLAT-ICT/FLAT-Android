package com.websarva.wings.android.flat

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Observer
import org.altbeacon.beacon.*
import org.altbeacon.bluetooth.BluetoothMedic
import android.app.PendingIntent




class BeaconDetectionService : Service(), RangeNotifier, MonitorNotifier {
    //TODO: Beaconに関してはActivityで動いたコードをそのまま貼ってある(コメントアウト状態)ので、これをforeground serviceとして動かす
    private lateinit var beaconManager: BeaconManager
    private lateinit var region: Region


    // public interface RangeNotifierのメンバ関数
    // Beaconの情報を取得する
    override fun didRangeBeaconsInRegion(beacons: MutableCollection<Beacon>?, region: Region?) {
        for (beacon: Beacon in beacons!!) {
            Log.d("didRangeBeaconsInRegion", "$beacon about ${beacon.distance} meters away")
            //TODO: distanceでソートして一番近いものをPOSTする。beacon.id1はUUID, beacon.id2はMajor, beacon.id3はMinor
        }
    }

    // ここから下3つはpublic interface MonitorNotifierのメンバ関数
    // ビーコン領域への入場を検知
    override fun didEnterRegion(region: Region?) {
        Log.d("iBeacon:Enter", "Region$region")
    }

    // ビーコン領域からの退場を検知
    override fun didExitRegion(region: Region?) {
        Log.d("iBeacon:Exit", "Region$region")
    }

    // ビーコン領域への入退場のステータス変化を検知
    // ビーコン領域の中に入れば1(INSIDE), 外に出れば0(OUTSIDE)
    override fun didDetermineStateForRegion(state: Int, region: Region?) {
        Log.d("iBeacon:Determine", "Determine State$state, Region$region")
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        BeaconManager.setDebug(true)

        region = Region("", null, null, null)
        beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager.also {
            it.beaconParsers.clear()
            it.beaconParsers.add(
                BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")
            )
            it.addMonitorNotifier(this)
            it.addRangeNotifier(this)
            it.startMonitoring(region)
            it.startRangingBeacons(region)
            it.foregroundBetweenScanPeriod = 1000L
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channelId = "FLAT_DETECT_BEACON"
        createNotificationChannel(channelId)

        val notificationIntent = Intent(this, MainActivity::class.java)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, channelId)
            .setContentTitle("FLAT")
            .setContentText("滞在場所を更新しています…")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        startForeground(1, builder.build())

        return START_STICKY
    }

    private fun createNotificationChannel(channelId: String) {
        val name = "ビーコン検知サービス起動中の通知"
        val descriptionText = "ビーコン検知を知らせるための通知です"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}