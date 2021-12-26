package com.rolf.day01

import com.rolf.Day
import com.rolf.util.splitLines

fun main() {
    Day01().run()
}

class Day01 : Day() {
    override fun getDay(): Int {
        return 1
    }

    override fun solve1(lines: List<String>) {
        val characters = splitLines(lines, chunkSize = 1).flatten()
        println(characters.map { if (it == "(") 1 else -1 }.sum())
    }

    override fun solve2(lines: List<String>) {
        val characters = splitLines(lines, chunkSize = 1).flatten()
        var floor = 0
        for ((index, character) in characters.withIndex()) {
            if (character == "(") floor++
            else floor--
            if (floor < 0) {
                println(index + 1)
                return
            }
        }
    }
}
