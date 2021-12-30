package com.rolf.day17

import com.rolf.Day
import com.rolf.util.getCombinations

fun main() {
    Day17().run()
}

class Day17 : Day() {
    override fun getDay(): Int {
        return 17
    }

    override fun solve1(lines: List<String>) {
        println(getMatchingContainers(lines).count())
    }

    override fun solve2(lines: List<String>) {
        val matchingContainers = getMatchingContainers(lines)

        val groupedBySize = matchingContainers.groupBy { it.size }
        val minSize = groupedBySize.minOf { it.key }
        println(groupedBySize[minSize]?.size)
    }

    private fun getMatchingContainers(lines: List<String>): List<List<Int>> {
        val containers = lines.map { it.toInt() }
        return getCombinations(containers).filter { it.sum() == 150 }
    }
}
