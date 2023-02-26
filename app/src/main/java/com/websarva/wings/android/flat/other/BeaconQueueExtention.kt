package com.websarva.wings.android.flat.other

import org.altbeacon.beacon.Beacon
import java.util.Queue

private data class BeaconIdentifier(val major: Int, val minor: Int)

// Queue内のBeaconの中からもっともRSSIの平均値が大きいものを選ぶ
// さらにその中で最もRSSIの値が大きいBeaconを返す
// Queueが空ならnullを返す
fun Queue<Beacon>.averageMaxRssi(): Beacon? {
    if (this.size == 0) return null

    val beaconRssiSum: MutableMap<BeaconIdentifier, Int> = mutableMapOf()
    val beaconCounter: MutableMap<BeaconIdentifier, Int> = mutableMapOf()

    this.forEach {x ->
        val identifier = BeaconIdentifier(x.id2.toInt(), x.id3.toInt())

        if (!beaconRssiSum.contains(identifier)) {
            beaconRssiSum[identifier] = x.rssi
            beaconCounter[identifier] = 1
        }
        else {
            beaconRssiSum[identifier] = beaconRssiSum[identifier]!! + x.rssi
            beaconCounter[identifier] = beaconCounter[identifier]!! + 1
        }
    }

    var currentMaxAverage = 0.0
    var resIdentifier = BeaconIdentifier(0, 0)

    beaconRssiSum.forEach { (k, v) ->
        val averageRssi = v.toDouble() / beaconCounter[k]!!.toDouble()
        if (currentMaxAverage < averageRssi) {
            currentMaxAverage = averageRssi
            resIdentifier = k
        }
    }

    return this
        .filter { BeaconIdentifier(it.id2.toInt(), it.id3.toInt()) == resIdentifier }
        .maxBy { it.rssi }
}