package com.rolf.day12

import com.google.gson.Gson
import com.rolf.Day

fun main() {
    Day12().run()
}

class Day12 : Day() {
    override fun getDay(): Int {
        return 12
    }

    override fun solve1(lines: List<String>) {
        var sum = 0L
        for (line in lines) {
            val matches = "-?\\d+".toRegex().findAll(line)
            sum += matches.map { it.value.toLong() }.sum()
        }
        println(sum)
    }

    override fun solve2(lines: List<String>) {
        val gson = Gson()
        val json = gson.fromJson("[${lines[0]}]", Array::class.java)
        println(sum(json).toInt())
    }

    private fun sum(json: Any?): Double {
        return when (json) {
            is Array<*> -> json.map { sum(it) }.sum()
            is ArrayList<*> -> json.map { sum(it) }.sum()
            is Map<*, *> -> {
                if (!json.values.contains("red")) {
                    json.map { sum(it.value) }.sum()
                } else {
                    0.0
                }
            }
            is Double -> json
            is String -> 0.0
            else -> throw Exception("Unknown type: $json")
        }
    }
}
