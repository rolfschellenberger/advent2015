package com.rolf.day11

import com.rolf.Day

fun main() {
    Day11().run()
}

class Day11 : Day() {
    override fun getDay(): Int {
        return 11
    }

    override fun solve1(lines: List<String>) {
        val password = Password(lines[0].toCharArray())
        while (!password.isValid()) {
            password.increment()
        }
        println(password)
    }

    override fun solve2(lines: List<String>) {
        val password = Password(lines[0].toCharArray())
        while (!password.isValid()) {
            password.increment()
        }
        password.increment()
        while (!password.isValid()) {
            password.increment()
        }
        println(password)
    }
}

class Password(var input: CharArray) {

    fun increment() {
        for (i in input.lastIndex downTo 0) {
            var newChar = input[i] + 1
            val overflow = newChar > MAX_CHAR

            if (overflow) {
                newChar = MIN_CHAR
            }
            input[i] = newChar

            if (!overflow) break
        }
    }

    fun isValid(): Boolean {
        if (!hasStraights()) return false
        if (hasInvalidChars()) return false
        if (!hasPairs()) return false
        return true
    }

    private fun hasStraights(): Boolean {
        var index = 0
        while (index < input.size - 2) {
            val one = input[index]
            val two = input[index + 1]
            val three = input[index + 2]
            if (one + 1 == two && two + 1 == three) {
                return true
            }
            index++
        }
        return false
    }

    private fun hasInvalidChars(): Boolean {
        return FORBIDDEN_LETTERS.intersect(input.toSet()).isNotEmpty()
    }

    private fun hasPairs(): Boolean {
        var pairs = 0
        var index = 0
        while (index < input.size - 1) {
            if (input[index] == input[index + 1]) {
                pairs++
                index++
            }
            index++
        }
        return pairs >= 2
    }

    override fun toString(): String {
        return String(input)
    }

    companion object {
        const val MIN_CHAR = 'a'
        const val MAX_CHAR = 'z'
        val FORBIDDEN_LETTERS = setOf('i', 'o', 'l')
    }

}