package com.rolf.day25

import com.rolf.Day

fun main() {
    Day25().run()
}

class Day25 : Day() {
    override fun getDay(): Int {
        return 25
    }

    override fun solve1(lines: List<String>) {
        // To continue, please consult the code grid in the manual.  Enter the code at row 2978, column 3083.
        val x = 3083
        val y = 2978

        // 5,7
        // x first: 1+2+3+4+5 = 15
        // y next: 15 + 5+6+7+8+9+10 = 60

        // 2978,3083
        // x first: 1..2978
        // y next: 2978..(2978+3083-2)
        val xSum = (1..x).sum()
        val ySum = (x..(x + y - 2)).sum()
        val n = xSum + ySum

        var value = 20151125L
//        println("1: $value")
        for (i in 2..n) {
            value = value * 252533L % 33554393L
//            println("$i: $value")
        }
        println(value)
    }

    override fun solve2(lines: List<String>) {
    }
}
