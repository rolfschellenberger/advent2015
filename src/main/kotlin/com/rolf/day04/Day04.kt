package com.rolf.day04

import com.rolf.Day
import java.security.MessageDigest
import java.util.*
import javax.xml.bind.DatatypeConverter

fun main() {
    Day04().run()
}

class Day04 : Day() {
    override fun getDay(): Int {
        return 4
    }

    override fun solve1(lines: List<String>) {
        val key = lines[0]
        println(findWithPrefix(key, FIVE_ZEROS))
    }

    override fun solve2(lines: List<String>) {
        val key = lines[0]
        println(findWithPrefix(key, SIX_ZEROS))
    }

    private fun findWithPrefix(key: String, prefix: String): Int {
        for (i in 1 until Int.MAX_VALUE) {
            val md5 = md5("$key$i")
            if (md5.startsWith(prefix)) {
                return i
            }
        }
        return 0
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        md.update(input.toByteArray())
        val digest = md.digest()
        return DatatypeConverter.printHexBinary(digest).uppercase(Locale.getDefault())
    }

    companion object {
        const val FIVE_ZEROS = "00000"
        const val SIX_ZEROS = "000000"
    }
}
