package com.rolf.day24

import com.rolf.Day
import com.rolf.util.getCombinations
import kotlin.math.abs

fun main() {
    Day24().run()
}

class Day24 : Day() {
    override fun getDay(): Int {
        return 24
    }

    private var minLength1 = Int.MAX_VALUE
    private var groupSum1 = 0L
    private var minQuantum1 = Long.MAX_VALUE
    private val options1 = mutableListOf<List<Long>>()

    override fun solve1(lines: List<String>) {
        val packages = lines.map { it.toLong() }
        val sum = packages.sum()
        groupSum1 = sum / 3

        getCombinations(packages, ::receiveCombination1, ::shouldAbortCombination1)
        println(minQuantum1)
    }

    private fun shouldAbortCombination1(combination: List<Long>): Boolean {
        if (combination.sum() > groupSum1) {
            return true
        }
        if (combination.size > minLength1) {
            return true
        }
        return false
    }

    private fun receiveCombination1(combination: List<Long>) {
        if (combination.sum() == groupSum1) {
            if (combination.size < minLength1) {
                minLength1 = combination.size
                options1.clear()
            }
            if (combination.size == minLength1) {
                options1.add(combination)
                val quantum = abs(combination.reduce(Long::times))
                minQuantum1 = minOf(minQuantum1, quantum)
            }
        }
    }

    private var minLength2 = Int.MAX_VALUE
    private var groupSum2 = 0L
    private var minQuantum2 = Long.MAX_VALUE
    private val options2 = mutableListOf<List<Long>>()

    override fun solve2(lines: List<String>) {
        val packages = lines.map { it.toLong() }
        val sum = packages.sum()
        groupSum2 = sum / 4

        getCombinations(packages, ::receiveCombination2, ::shouldAbortCombination2)
        println(minQuantum2)
    }

    private fun shouldAbortCombination2(combination: List<Long>): Boolean {
        if (combination.sum() > groupSum2) {
            return true
        }
        if (combination.size > minLength2) {
            return true
        }
        return false
    }

    private fun receiveCombination2(combination: List<Long>) {
        if (combination.sum() == groupSum2) {
            if (combination.size < minLength2) {
                minLength2 = combination.size
                options2.clear()
            }
            if (combination.size == minLength2) {
                options2.add(combination)
                val quantum = abs(combination.reduce(Long::times))
                minQuantum2 = minOf(minQuantum2, quantum)
            }
        }
    }
}
