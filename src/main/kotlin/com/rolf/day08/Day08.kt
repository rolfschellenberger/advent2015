package com.rolf.day08

import com.rolf.Day
import org.apache.commons.text.StringEscapeUtils

fun main() {
    Day08().run()
}

class Day08 : Day() {
    override fun getDay(): Int {
        return 8
    }

    override fun solve1(lines: List<String>) {
        var total = 0L
        for (line in lines) {
            val (length, stringLength) = determineLength(line)
            total += length - stringLength
        }
        println(total)
    }

    private fun determineLength(line: String): Pair<Int, Int> {
        return line.length to unescapedLength(line)
    }

    private fun unescapedLength(line: String): Int {
        var length = 0

        var index = 1
        while (index < line.length - 1) {
            val char = line[index]
            if (char == '\\') {
                if (line[index + 1] == '\\' || line[index + 1] == '"') {
                    index++
                } else if (line[index + 1] == 'x') {
                    index += 3
                }
            }
            length++
            index++
        }
        return length
    }

    override fun solve2(lines: List<String>) {
        var total = 0L
        for (line in lines) {
            total += StringEscapeUtils.escapeEcmaScript(line).length - line.length + 2
        }
        println(total)
    }
}
