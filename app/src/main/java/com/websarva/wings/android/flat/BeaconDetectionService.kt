package com.websarva.wings.android.flat

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import org.altbeacon.beacon.*
import android.app.PendingIntent
import com.websarva.wings.android.flat.api.PostData
import com.websarva.wings.android.flat.repository.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Exception


class BeaconDetectionService : Service(), RangeNotifier, MonitorNotifier {
    private val repository = ApiRepository.instance
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    private lateinit var beaconManager: BeaconManager
    private lateinit var region: Region
    private lateinit var postData: PostData.PostBeacon


    // public interface RangeNotifierのメンバ関数
    // Beaconの情報を取得する
    override fun didRangeBeaconsInRegion(beacons: MutableCollection<Beacon>?, region: Region?) {
        var nearBeacon: Beacon? = null
        for (beacon: Beacon in beacons!!) {
            Log.d("didRangeBeaconsInRegion", "$beacon about ${beacon.distance} meters away")
            //一番近いものをnearBeaconに持つ
            if (nearBeacon == null) {
                nearBeacon = beacon
            }
            else if (nearBeacon.distance > beacon.distance) {
                nearBeacon = beacon
            }
        }
        // TODO:IDをroom等で内部に保存しrepositoryから持ってくる
        if (nearBeacon != null) {
            postData = PostData.PostBeacon(
                user_id = 1,
                major = nearBeacon.id2.toInt(),
                minor = nearBeacon.id3.toInt(),
                rssi = nearBeacon.rssi
            )
            Log.d("postBeacon", "$postData")
            scope.launch {
                postBeacon(postData)
            }
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
        postData = PostData.PostBeacon(
            user_id = 1,
            major = 0,
            minor = -1,
            rssi = 0,
        )
        Log.d("postExitBeacon", "$postData")
        scope.launch {
            postBeacon(postData)
        }
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

//        BeaconManager.setDebug(true)

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
            it.foregroundScanPeriod = 1100L
            it.foregroundBetweenScanPeriod = 30000L
            it.backgroundScanPeriod = 1100L
            it.backgroundBetweenScanPeriod = 60000L
            it.updateScanPeriods()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channelId = "FLAT_DETECT_BEACON"
        createNotificationChannel(channelId)

        val notifyIntent = Intent(this, MainActivity::class.java).apply {
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =  PendingIntent.getActivity(this, 0, notifyIntent, 0)

        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("FLAT Service")
            .setContentText("滞在場所を更新します")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)

        return START_STICKY
    }

    private fun createNotificationChannel(channelId: String) {
        val name = "ビーコン検知サービス起動中の通知"
        val descriptionText = "ビーコン検知を知らせるための通知です"
        // 通知音を鳴らさない
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private suspend fun postBeacon(postData: PostData.PostBeacon) {
        try {
            val response = repository.postBeacon(postData)
            if (response.isSuccessful) {
                Log.d("postBeaconSuccess", "${response}\n${response.body()}\n")
            } else {
                Log.d("postBeaconFailure", "$response")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}