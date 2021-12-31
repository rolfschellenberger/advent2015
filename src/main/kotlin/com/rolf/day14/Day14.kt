package com.rolf.day14

import com.rolf.Day
import com.rolf.util.splitLine

fun main() {
    Day14().run()
}

class Day14 : Day() {
    override fun getDay(): Int {
        return 14
    }

    override fun solve1(lines: List<String>) {
        val deer = parseDeer(lines)

        var maxDistance = 0
        for (d in deer) {
            val distance = d.distanceAfter(2503)
            maxDistance = maxOf(maxDistance, distance)
        }
        println(maxDistance)
    }

    override fun solve2(lines: List<String>) {
        val deer = parseDeer(lines)
        val points = mutableMapOf<String, Int>()
        for (d in deer) {
            points[d.name] = 0
        }

        for (sec in 1..2503) {
            val lead = findLeaders(deer, sec)
            for (l in lead) {
                points[l.name] = points[l.name]!! + 1
            }
        }

        println(points.maxOf { it.value })
    }

    private fun findLeaders(deer: MutableList<Deer>, sec: Int): List<Deer> {
        val score = mutableMapOf<Int, MutableList<Deer>>()
        var maxDistance = 0
        for (d in deer) {
            val distance = d.distanceAfter(sec)
            maxDistance = maxOf(maxDistance, distance)
            score.putIfAbsent(distance, mutableListOf())
            score[distance]?.add(d)
        }

        return score[maxDistance] ?: emptyList()
    }

    private fun parseDeer(lines: List<String>): MutableList<Deer> {
        val deer = mutableListOf<Deer>()
        for (line in lines) {
            val parts = splitLine(line, " ")
            val name = parts[0]
            val speedDistance = parts[3].toInt()
            val speedTime = parts[6].toInt()
            val restTime = parts[13].toInt()
            deer.add(Deer(name, speedDistance, speedTime, restTime))
        }
        return deer
    }
}

data class Deer(val name: String, val speedDistance: Int, val speedTime: Int, val restTime: Int) {
    fun distanceAfter(sec: Int): Int {
        val cycleTimes = sec / (speedTime + restTime)
        val lastCycleTime = sec % (speedTime + restTime)

        val distancePerCycles = speedDistance * speedTime * cycleTimes
        val distanceLastCycle = speedDistance * minOf(lastCycleTime, speedTime)
        return distancePerCycles + distanceLastCycle
    }
}