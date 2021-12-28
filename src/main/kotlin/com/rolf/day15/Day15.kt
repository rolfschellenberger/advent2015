package com.rolf.day15

import com.rolf.Day

fun main() {
    Day15().run()
}

class Day15 : Day() {
    override fun getDay(): Int {
        return 15
    }

    override fun solve1(lines: List<String>) {
        var maxValue = 0
        for (i in 0..100) {
            for (j in 0..100) {
                for (k in 0..100) {
                    val l = 100 - i - j - k
                    if (l > 0) {
                        val a = maxOf(0, 2 * i)
                        val b = maxOf(0, 5 * j + -1 * l)
                        val c = maxOf(0, -2 * i + -3 * j + 5 * k)
                        val d = maxOf(0, -1 * k + 5 * l)
                        val mul = a * b * c * d
                        if (mul > 0) {
                            maxValue = maxOf(maxValue, mul)
                        }
                    }
                }
            }
        }
        println(maxValue)
    }

    override fun solve2(lines: List<String>) {
        var maxValue = 0
        for (i in 0..100) {
            for (j in 0..100) {
                for (k in 0..100) {
                    val l = 100 - i - j - k
                    if (l > 0) {
                        val a = maxOf(0, 2 * i)
                        val b = maxOf(0, 5 * j + -1 * l)
                        val c = maxOf(0, -2 * i + -3 * j + 5 * k)
                        val d = maxOf(0, -1 * k + 5 * l)
                        val mul = a * b * c * d
                        val cal = 3 * i + 3 * j + 8 * k + 8 * l
                        if (mul > 0 && cal == 500) {
                            maxValue = maxOf(maxValue, mul)
                        }
                    }
                }
            }
        }
        println(maxValue)
    }
}
