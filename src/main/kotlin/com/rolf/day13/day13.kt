package com.rolf.day13

import com.rolf.Day
import com.rolf.util.MatrixString
import com.rolf.util.Point
import com.rolf.util.getPermutations
import com.rolf.util.splitLine

fun main() {
    Day13().run()
}

class Day13 : Day() {
    override fun getDay(): Int {
        return 13
    }

    override fun solve1(lines: List<String>) {
        val (arrangements, people) = getArrangementsAndPeople(lines)
        val maxHappiness = calculateMaxHappiness(people, arrangements)
        println(maxHappiness)
    }

    override fun solve2(lines: List<String>) {
        val (arrangements, people) = getArrangementsAndPeople(lines)
        people.add("me")
        val maxHappiness = calculateMaxHappiness(people, arrangements)
        println(maxHappiness)
    }

    private fun getArrangementsAndPeople(lines: List<String>): Pair<MutableList<Arrangement>, MutableSet<String>> {
        val arrangements = mutableListOf<Arrangement>()
        val people = mutableSetOf<String>()
        for (line in lines) {
            val words = splitLine(line.removeSuffix("."), " ")
            val from = words[0]
            val to = words[10]
            val operator = words[2]
            val value = words[3].toInt()
            arrangements.add(Arrangement(from, to, if (operator == "gain") value else -value))
            people.add(from)
            people.add(to)
        }
        return Pair(arrangements, people)
    }

    private fun calculateMaxHappiness(people: MutableSet<String>, arrangements: MutableList<Arrangement>): Int {
        val permutations = getPermutations(people.toList())
        var maxHappiness = Int.MIN_VALUE
        for (seating in permutations) {
            val happiness = getHappiness(arrangements, seating)
            maxHappiness = maxOf(maxHappiness, happiness)
        }
        return maxHappiness
    }

    private fun getHappiness(arrangements: List<Arrangement>, seating: List<String>): Int {
        var happiness = 0

        val table = MatrixString.build(listOf(seating))
        val y = 0
        for ((x, person) in seating.withIndex()) {
            val right = table.get(table.getRight(Point(x, y), true)!!)
            happiness += getHappiness(arrangements, person, right)
        }
        return happiness
    }

    private fun getHappiness(arrangements: List<Arrangement>, person: String, right: String): Int {
        var happiness = 0
        for (arrangement in arrangements) {
            if (arrangement.a == person && arrangement.b == right) {
                happiness += arrangement.value
            }
            if (arrangement.a == right && arrangement.b == person) {
                happiness += arrangement.value
            }
        }
        return happiness
    }
}

data class Arrangement(val a: String, val b: String, val value: Int)
