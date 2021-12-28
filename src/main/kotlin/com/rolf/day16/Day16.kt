package com.rolf.day16

import com.rolf.Day

fun main() {
    Day16().run()
}

class Day16 : Day() {
    override fun getDay(): Int {
        return 16
    }

    override fun solve1(lines: List<String>) {
        val aunts = parseAunts(lines)
        removeAuntsWithout(aunts, "children", 3)
        removeAuntsWithout(aunts, "cats", 7)
        removeAuntsWithout(aunts, "samoyeds", 2)
        removeAuntsWithout(aunts, "pomeranians", 3)
        removeAuntsWithout(aunts, "akitas", 0)
        removeAuntsWithout(aunts, "vizslas", 0)
        removeAuntsWithout(aunts, "goldfish", 5)
        removeAuntsWithout(aunts, "trees", 3)
        removeAuntsWithout(aunts, "cars", 2)
        removeAuntsWithout(aunts, "perfumes", 1)
        println(aunts)
    }

    override fun solve2(lines: List<String>) {
        val aunts = parseAunts(lines)
        removeAuntsWithout2(aunts, "children", 3)
        removeAuntsWithout2(aunts, "cats", 7)
        removeAuntsWithout2(aunts, "samoyeds", 2)
        removeAuntsWithout2(aunts, "pomeranians", 3)
        removeAuntsWithout2(aunts, "akitas", 0)
        removeAuntsWithout2(aunts, "vizslas", 0)
        removeAuntsWithout2(aunts, "goldfish", 5)
        removeAuntsWithout2(aunts, "trees", 3)
        removeAuntsWithout2(aunts, "cars", 2)
        removeAuntsWithout2(aunts, "perfumes", 1)
        println(aunts)
    }

    private fun parseAunts(lines: List<String>): MutableList<Aunt> {
        val aunts = mutableListOf<Aunt>()
        for (line in lines) {
            val splitPos = line.indexOfFirst { it == ':' }
            val sue = line.substring(0, splitPos)
            val properties = line.substring(splitPos + 2, line.length)
            val id = sue.split(" ").last().removeSuffix(":").toInt()
            val propertiesList = properties.split(", ")
            val props = mutableMapOf<String, Int>()
            for (property in propertiesList) {
                val (name, value) = property.split(": ")
                props[name] = value.toInt()
            }
            aunts.add(Aunt(id, props))
        }
        return aunts
    }

    private fun removeAuntsWithout(aunts: MutableList<Aunt>, property: String, value: Int) {
        val toRemove = mutableListOf<Aunt>()
        for (aunt in aunts) {
            if (aunt.properties.containsKey(property)) {
                if (aunt.properties[property]!! != value) {
                    toRemove.add(aunt)
                }
            }
        }
        aunts.removeAll(toRemove)
    }

    private fun removeAuntsWithout2(aunts: MutableList<Aunt>, property: String, value: Int) {
        val toRemove = mutableListOf<Aunt>()
        for (aunt in aunts) {
            if (aunt.properties.containsKey(property)) {
                val propValue = aunt.properties[property]!!
                when (property) {
                    "cats", "trees" -> {
                        if (propValue <= value) {
                            toRemove.add(aunt)
                        }
                    }
                    "pomeranians", "goldfish" -> {
                        if (propValue >= value) {
                            toRemove.add(aunt)
                        }
                    }
                    else -> {
                        if (propValue != value) {
                            toRemove.add(aunt)
                        }
                    }
                }
            }
        }
        aunts.removeAll(toRemove)
    }
}

data class Aunt(val id: Int, val properties: Map<String, Int>)