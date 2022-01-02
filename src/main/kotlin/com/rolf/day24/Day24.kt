package com.rolf.day24

import com.rolf.Day
import com.rolf.util.getCombinations

fun main() {
    Day24().run()
}

class Day24 : Day() {
    override fun getDay(): Int {
        return 24
    }

    override fun solve1(lines: List<String>) {
        val packages = lines.map { it.toInt() }
        val sum = packages.sum()
        groupSum1 = sum / 3
        groupSum2 = sum / 4

        // TODO: early abort?
        getCombinations(packages, ::receiveCombination)
        val sortedOptions = options1.groupBy { it.size }
        val minLength = sortedOptions.keys.minOrNull()!!
        val minOptions = sortedOptions[minLength]!!
        val sortedByQuantum = minOptions.map { it.map { it.toLong() }.reduce(Long::times) to it }.toMap()
        val minQuantum = sortedByQuantum.keys.minOrNull()
        println(minQuantum)
    }

    private var groupSum1 = 0
    private var groupSum2 = 0
    private val options1 = mutableListOf<List<Int>>()
    private val options2 = mutableListOf<List<Int>>()

    private fun receiveCombination(combination: List<Int>) {
        if (combination.sum() == groupSum1) {
            options1.add(combination)
        }
        if (combination.sum() == groupSum2) {
            options2.add(combination)
        }
    }

    override fun solve2(lines: List<String>) {
        // TODO: early abort?
        val sortedOptions = options2.groupBy { it.size }
        val minLength = sortedOptions.keys.minOrNull()!!
        val minOptions = sortedOptions[minLength]!!
        val sortedByQuantum = minOptions.map { it.map { it.toLong() }.reduce(Long::times) to it }.toMap()
        val minQuantum = sortedByQuantum.keys.minOrNull()
        println(minQuantum)
    }
}
