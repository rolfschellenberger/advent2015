package com.rolf.day21

import com.rolf.Day
import com.rolf.util.getCombinations
import com.rolf.util.groupLines
import com.rolf.util.splitLines

fun main() {
    Day21().run()
}

class Day21 : Day() {
    override fun getDay(): Int {
        return 21
    }

    override fun solve1(lines: List<String>) {
        val (bossInfo, _) = groupLines(lines, "")
        val (bossHP, bossDamage, bossArmor) = splitLines(bossInfo, ": ")
            .flatMap { listOf(it[1]) }
            .map { it.toInt() }

        // From strength to cost
        val damageItems = listOf(
            1 to 25,
            2 to 50,
            3 to 100,
            4 to 8,
            5 to 10,
            6 to 25,
            7 to 40,
            8 to 74
        )
        val armorItems = listOf(
            1 to 13,
            2 to 31,
            3 to 53
        )
        val ringItems = listOf(
            1 to 20,
            2 to 40,
            3 to 80
        )

        val hp = 100

        for (damage in 0..11) {
            for (armor in 0..11) {
                val me = Player("Me", hp, damage, armor)
                val boss = Player("Boss", bossHP, bossDamage, bossArmor)
                val winner = playGame(me, boss)
                if (winner == me) {
                    println("damage=$damage, armor=$armor = $winner")
                    break
                }
            }
        }
        println()
        for (armor in 0..11) {
            for (damage in 0..11) {
                val me = Player("Me", hp, damage, armor)
                val boss = Player("Boss", bossHP, bossDamage, bossArmor)
                val winner = playGame(me, boss)
                if (winner == me) {
                    println("damage=$damage, armor=$armor = $winner")
                    break
                }
            }
        }
        // Min damage = 4 and damage + armor >= 11
        // hp: 100 vs 109
        // da:     vs 8
        // ar:     vs 2

        // Pick damage
        var minPrice = Int.MAX_VALUE
        for (damage in damageItems) {
            for (armor in armorItems) {
                val combinations = getCombinations(ringItems)
                for (combination in combinations) {
                    // Check if score of combination is high enough (>= 11)
                    val scoreCombination = combinationScore(combination)
                    if (damage.first + armor.first + scoreCombination >= 11) {
                        val price = damage.second + armor.second + combinationPrice(combination)
                        if (price < minPrice) {
                            minPrice = price
                        }
                    }
                }
            }
        }
        println(minPrice)
    }

    private fun combinationScore(combination: List<Pair<Int, Int>>): Int {
        var score = 0
        for (c in combination) {
            score += c.first
        }
        return score
    }

    private fun combinationPrice(combination: List<Pair<Int, Int>>): Int {
        var price = 0
        for (c in combination) {
            price += c.second
        }
        return price
    }

    private fun playGame(attacker: Player, defender: Player): Player {
        // Stop condition
        if (attacker.hp <= 0) {
            return defender
        }
        if (defender.hp <= 0) {
            return attacker
        }

        val hpDamage = maxOf(attacker.damage - defender.armor, 1)
        defender.hp -= hpDamage
//        println(attacker)
//        println(defender)
//        println()
        return playGame(defender, attacker)
    }

    override fun solve2(lines: List<String>) {
        // From strength to cost
        val damageItems = listOf(
            4 to 8,
            5 to 10,
            6 to 25,
            7 to 40,
            8 to 74
        )
        val ringItems = listOf(
            1 to 25,
            2 to 50,
            3 to 100
        )
        val armorItems = listOf(
            1 to 13,
            2 to 31,
            3 to 53,
            1 to 20,
            2 to 40,
            3 to 80
        )

        // Pick damage
        var maxPrice = 0
        for (damage in damageItems) {
            for (ring in ringItems) {
                val combinations = getCombinations(armorItems)
                for (combination in combinations) {
                    // Check if score of combination is low enough (< 11)
                    val scoreCombination = combinationScore(combination)
                    if (damage.first + ring.first + scoreCombination < 11) {
                        val price = damage.second + ring.second + combinationPrice(combination)
                        if (price > maxPrice) {
                            maxPrice = price
                        }
                    }
                }
            }
        }

        println(maxPrice)
    }
}

data class Player(val name: String, var hp: Int, var damage: Int, val armor: Int)
