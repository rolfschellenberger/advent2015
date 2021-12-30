package com.rolf.day19

import com.rolf.Day
import com.rolf.util.groupLines

fun main() {
    Day19().run()
}

class Day19 : Day() {
    override fun getDay(): Int {
        return 19
    }

    override fun solve1(lines: List<String>) {
        val (replacementsStrings, molecules) = groupLines(lines, "")
        val replacements = parseReplacements(replacementsStrings)
        val molecule = molecules[0]

        val newMolecules = step(molecule, replacements)
        println(newMolecules.size)
    }

    private fun parseReplacements(replacementsStrings: List<String>): List<Replacement> {
        return replacementsStrings
            .map {
                val (from, to) = it.split(" => ")
                from to to
            }
            .map {
                Replacement(it.first, it.second)
            }
    }

    private fun step(molecule: String, replacements: List<Replacement>): Set<String> {
        val set = mutableSetOf<String>()
        for (replacement in replacements) {
            val matches = replacement.from.toRegex(RegexOption.LITERAL).findAll(molecule)
            for (match in matches) {
                val newMolecule = molecule.replaceRange(match.range, replacement.to)
                set.add(newMolecule)
            }
        }
        return set
    }

    override fun solve2(lines: List<String>) {
        val (replacementsStrings, molecules) = groupLines(lines, "")
        val replacements = parseReplacements(replacementsStrings)
        var molecule = molecules[0]

        var steps = 0
        while (molecule != "e") {
            for (replacement in replacements) {
                if (molecule.contains(replacement.to)) {
                    val lastIndex = molecule.lastIndexOf(replacement.to)
                    val replaceRange = lastIndex until lastIndex + replacement.to.length
                    molecule = molecule.replaceRange(replaceRange, replacement.from)
                    steps++
                }
            }
        }
        println(steps)
    }
}

data class Replacement(val from: String, val to: String)
