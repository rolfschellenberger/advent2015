package com.rolf.day10

import com.rolf.Day

fun main() {
    Day10().run()
}

class Day10 : Day() {
    override fun getDay(): Int {
        return 10
    }

    override fun solve1(lines: List<String>) {
        println(play(lines[0], 40).length)
    }

    override fun solve2(lines: List<String>) {
        println(play(lines[0], 50).length)
    }

    private fun play(input: String, times: Int): String {
        var line = input
        for (i in 0 until times) {
            line = play(line)
        }
        return line
    }

    private fun play(line: String): String {
        var index = 1
        var previousChar = line[0]
        var count = 1
        val output = StringBuilder()
        while (index < line.length) {
            val currentChar = line[index]
            if (currentChar == previousChar) {
                count++
            } else {
                // Wrap up
                output.append(count).append(previousChar)
                count = 1
            }
            previousChar = currentChar
            index++
        }

        // Wrap up
        output.append(count).append(previousChar)
        return output.toString()
    }
}
