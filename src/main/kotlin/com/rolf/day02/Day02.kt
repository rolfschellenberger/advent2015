package com.rolf.day02

import com.rolf.Day
import com.rolf.util.splitLines

fun main() {
    Day02().run()
}

class Day02 : Day() {
    override fun getDay(): Int {
        return 2
    }

    override fun solve1(lines: List<String>) {
        val wrapLines = splitLines(lines, "x")
        var total = 0L
        for (wrapLine in wrapLines) {
            val (l, w, h) = wrapLine.map { it.toInt() }
            val s1 = l * w
            val s2 = w * h
            val s3 = h * l
            val surface = 2 * s1 + 2 * s2 + 2 * s3
            val extra = minOf(s1, s2, s3)
            total += surface + extra
        }
        println(total)
    }

    override fun solve2(lines: List<String>) {
        val wrapLines = splitLines(lines, "x")
        var total = 0L
        for (wrapLine in wrapLines) {
            val wrapDimensions = wrapLine.map { it.toInt() }
            val ribbon = wrapDimensions.sorted().subList(0, 2).sum() * 2
            val bow = wrapDimensions.reduce(Int::times)
            total += ribbon + bow
        }
        println(total)
    }
}
