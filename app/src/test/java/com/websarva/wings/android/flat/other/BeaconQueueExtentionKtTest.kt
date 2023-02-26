package com.websarva.wings.android.flat.other

import org.altbeacon.beacon.Beacon
import org.junit.Assert.*
import org.junit.Test
import java.util.Queue
import java.util.ArrayDeque

// テストを書くときに、
// Kotlin.collections.ArrayDequeではなく、java.util.ArrayDequeを使って空のQueueを作る

class BeaconQueueExtentionKtTest {
    @Test
    fun BeaconQueueExtentionTest1() {
        //同じBeaconが複数あるとき、そのうちRSSIが最大なBeaconを返す
        val beaconData: List<Beacon> = listOf(
            Beacon.Builder().setId1("aaa").setId2("1").setId3("10").setRssi(10).build(),
            Beacon.Builder().setId1("bbb").setId2("2").setId3("20").setRssi(30).build(),
            Beacon.Builder().setId1("ccc").setId2("3").setId3("30").setRssi(20).build()
        )
        val a: Queue<Beacon> = ArrayDeque()
        beaconData.forEach { a.add(it) }

        assertEquals(a.averageMaxRssi(), beaconData[1])
    }

    @Test
    fun BeaconQueueExtentionTest2() {
        //同じBeaconが複数あるとき、そのうちRSSIが最大なBeaconを返す
        val beaconData: List<Beacon> = listOf(
            Beacon.Builder().setId1("aaa").setId2("1").setId3("10").setRssi(10).build(),
            Beacon.Builder().setId1("aaa").setId2("1").setId3("10").setRssi(20).build()
        )
        val a: Queue<Beacon> = ArrayDeque()
        beaconData.forEach { a.add(it) }

        assertEquals(a.averageMaxRssi(), beaconData[1])
    }

    @Test
    fun BeaconQueueExtentionTest3() {
        //RSSIの平均値が最大なBeaconを返す
        //ただし、平均が最大なBeaconの中でも最もRSSIが大きな値のものを返す
        val beaconData: List<Beacon> = listOf(
            Beacon.Builder().setId1("aaa").setId2("1").setId3("10").setRssi(10).build(),
            Beacon.Builder().setId1("aaa").setId2("1").setId3("10").setRssi(15).build(),
            Beacon.Builder().setId1("ccc").setId2("3").setId3("30").setRssi(15).build(),
            Beacon.Builder().setId1("ccc").setId2("3").setId3("30").setRssi(20).build()
        )
        val a: Queue<Beacon> = ArrayDeque()
        beaconData.forEach { a.add(it) }

        assertEquals(a.averageMaxRssi(), beaconData[3])
    }

    @Test
    fun BeaconQueueExtentionTest4() {
        //自明なケース (それしかないならそれ)
        val beacon = Beacon.Builder().setId1("aaa").setId2("1").setId3("10").setRssi(60).build()
        val a: Queue<Beacon> = ArrayDeque()
        a.add(beacon)

        assertEquals(a.averageMaxRssi(), beacon)
    }

    @Test
    fun BeaconQueueExtentionTest5() {
        //自明なケース (空ならnull)
        val a: Queue<Beacon> = ArrayDeque()
        assertEquals(a.averageMaxRssi(), null)
    }

    @Test
    fun BeaconQueueExtentionTest6() {
        // 大きめのケース; Queue<Beacon>.size == 30
        val beaconData: List<Beacon> = listOf(
            Beacon.Builder().setId1("aaa").setId2("1").setId3("10").setRssi(35).build(),
            Beacon.Builder().setId1("bbb").setId2("2").setId3("20").setRssi(58).build(),
            Beacon.Builder().setId1("ccc").setId2("3").setId3("30").setRssi(40).build(),

            Beacon.Builder().setId1("aaa").setId2("1").setId3("10").setRssi(20).build(),
            Beacon.Builder().setId1("bbb").setId2("2").setId3("20").setRssi(57).build(),
            Beacon.Builder().setId1("ccc").setId2("3").setId3("30").setRssi(51).build(),

            Beacon.Builder().setId1("aaa").setId2("1").setId3("10").setRssi(26).build(),
            Beacon.Builder().setId1("bbb").setId2("2").setId3("20").setRssi(51).build(),
            Beacon.Builder().setId1("ccc").setId2("3").setId3("30").setRssi(27).build(),

            Beacon.Builder().setId1("aaa").setId2("1").setId3("10").setRssi(50).build(),
            Beacon.Builder().setId1("bbb").setId2("2").setId3("20").setRssi(45).build(),
            Beacon.Builder().setId1("ccc").setId2("3").setId3("30").setRssi(35).build(),

            Beacon.Builder().setId1("aaa").setId2("1").setId3("10").setRssi(34).build(),
            Beacon.Builder().setId1("bbb").setId2("2").setId3("20").setRssi(47).build(),
            Beacon.Builder().setId1("ccc").setId2("3").setId3("30").setRssi(40).build(),

            Beacon.Builder().setId1("aaa").setId2("1").setId3("10").setRssi(39).build(),
            Beacon.Builder().setId1("bbb").setId2("2").setId3("20").setRssi(47).build(),
            Beacon.Builder().setId1("ccc").setId2("3").setId3("30").setRssi(33).build(),

            Beacon.Builder().setId1("aaa").setId2("1").setId3("10").setRssi(47).build(),
            Beacon.Builder().setId1("bbb").setId2("2").setId3("20").setRssi(42).build(),
            Beacon.Builder().setId1("ccc").setId2("3").setId3("30").setRssi(54).build(),

            Beacon.Builder().setId1("aaa").setId2("1").setId3("10").setRssi(45).build(),
            Beacon.Builder().setId1("bbb").setId2("2").setId3("20").setRssi(60).build(),
            Beacon.Builder().setId1("ccc").setId2("3").setId3("30").setRssi(37).build(),

            Beacon.Builder().setId1("aaa").setId2("1").setId3("10").setRssi(47).build(),
            Beacon.Builder().setId1("bbb").setId2("2").setId3("20").setRssi(21).build(),
            Beacon.Builder().setId1("ccc").setId2("3").setId3("30").setRssi(60).build(),

            Beacon.Builder().setId1("aaa").setId2("1").setId3("10").setRssi(34).build(),
            Beacon.Builder().setId1("bbb").setId2("2").setId3("20").setRssi(42).build(),
            Beacon.Builder().setId1("ccc").setId2("3").setId3("30").setRssi(56).build(),
        )

        val a: Queue<Beacon> = ArrayDeque()
        beaconData.forEach { a.add(it) }

        assertEquals(a.averageMaxRssi(), beaconData[22])
    }
}