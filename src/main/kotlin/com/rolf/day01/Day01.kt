package com.rolf.day01

import com.rolf.util.readLines
import com.rolf.util.splitLines

const val DAY = "01"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLines("/$DAY.txt")

    println("-- Part 1 --")
    solve1(lines)
    println("-- Part 2 --")
    solve2(lines)
}

fun solve1(lines: List<String>) {
    val characters = splitLines(lines, chunkSize = 1).flatten()
    println(characters.map { if (it == "(") 1 else -1 }.sum())
}

fun solve2(lines: List<String>) {
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
