package com.websarva.wings.android.flat

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Observer
import org.altbeacon.beacon.*
import org.altbeacon.bluetooth.BluetoothMedic

class BeaconService : Service(), RangeNotifier, MonitorNotifier {
    // TODO: Beaconに関してはActivityで動いたコードをそのまま貼ってある(コメントアウト状態)ので、これをforeground serviceとして動かす
//    private lateinit var beaconManager: BeaconManager
//    private lateinit var region: Region
//
    override fun onCreate() {
        super.onCreate()
//
//        BeaconManager.setDebug(true)
//
//        region = Region("", null, null, null)
//        beaconManager = BeaconManager.getInstanceForApplication(this)
//        beaconManager.also {
//            it.beaconParsers.clear()
//            it.beaconParsers.add(
//                BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")
//            )
//            it.addMonitorNotifier(this)
//            it.addRangeNotifier(this)
//            it.startMonitoring(region)
//            it.startRangingBeacons(region)
//            it.foregroundBetweenScanPeriod = 1000L
//        }
//
//        BluetoothMedic.getInstance().enablePowerCycleOnFailures(this)
//        BluetoothMedic.getInstance()
//            .enablePeriodicTests(this, BluetoothMedic.SCAN_TEST + BluetoothMedic.TRANSMIT_TEST)
    }


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
}