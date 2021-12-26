package com.rolf.day05

import com.rolf.Day

fun main() {
    Day05().run()
}

class Day05 : Day() {
    override fun getDay(): Int {
        return 5
    }

    override fun solve1(lines: List<String>) {
        println(lines.filter { isNice(it) }.count())
    }

    private fun isNice(line: String): Boolean {
        if (hasInvalidStrings(line)) return false
        if (!hasEnoughVowels(line)) return false
        if (!hasDoubleLetters(line)) return false
        return true
    }

    private fun hasEnoughVowels(line: String): Boolean {
        return line.filter { VOWELS.contains(it) }.count() >= 3
    }

    private fun hasDoubleLetters(line: String): Boolean {
        return line.filterIndexed { index, char -> index > 0 && char == line[index - 1] }.count() > 0
    }

    private fun hasInvalidStrings(line: String): Boolean {
        for (string in INVALID) {
            if (line.contains(string)) return true
        }
        return false
    }

    override fun solve2(lines: List<String>) {
        println(lines.filter { isNice2(it) }.count())
    }

    private fun isNice2(line: String): Boolean {
        if (!hasPairs(line)) return false
        if (!hasRepeatingLetterWithOneDiff(line)) return false
        return true
    }

    private fun hasPairs(line: String): Boolean {
        for ((index, _) in line.withIndex()) {
            if (index > 0) {
                val pair = line.substring(index - 1, index + 1)
                if (line.indexOf(pair, startIndex = index + 1) >= 0) {
                    return true
                }
            }
        }
        return false
    }

    private fun hasRepeatingLetterWithOneDiff(line: String): Boolean {
        return line.filterIndexed { index, char -> index > 1 && char == line[index - 2] }.count() > 0
    }

    companion object {
        val VOWELS = setOf('a', 'e', 'o', 'u', 'i')
        val INVALID = setOf("ab", "cd", "pq", "xy")
    }
}
