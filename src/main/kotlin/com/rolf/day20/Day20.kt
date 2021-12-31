package com.rolf.day20

import com.rolf.Day

fun main() {
    Day20().run()
}

class Day20 : Day() {
    override fun getDay(): Int {
        return 20
    }

    override fun solve1(lines: List<String>) {
        val minValue = lines[0].toInt()
        val maxHouse = minValue / 10

        val array = IntArray(maxHouse + 1)
        for (elf in 1..maxHouse) {
            for (house in elf..maxHouse step elf) {
                array[house] += elf * 10
            }
        }
        println(
            array
                .mapIndexed { index, value -> index to value }
                .filter { it.second >= minValue }
                .minByOrNull { it.first }?.first
        )
    }

    override fun solve2(lines: List<String>) {
        val minValue = lines[0].toInt()
        val maxHouse = minValue / 10

        val array = IntArray(maxHouse + 1)
        for (elf in 1..maxHouse) {
            for (house in elf..(minOf(50 * elf, maxHouse)) step elf) {
                array[house] += elf * 11
            }
        }
        println(
            array
                .mapIndexed { index, value -> index to value }
                .filter { it.second >= minValue }
                .minByOrNull { it.first }?.first
        )
    }
}
