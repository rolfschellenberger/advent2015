package com.rolf.day22

import com.rolf.Day
import com.rolf.util.getPermutations

fun main() {
    Day22().run()
}

class Day22 : Day() {
    override fun getDay(): Int {
        return 22
    }

    override fun solve1(lines: List<String>) {
        val bossHP = 71
        val bossDamage = 10
        val hp = 50
        val mana = 500



        val boss = Player("Boss", bossHP, bossDamage, 0, 0, emptyList())
        val me = Player(
            "Me", hp, 0, 0, mana, listOf(
                MagicMissile(),
                Drain(),
                Shield(),
                Poison(),
                Recharge()
            )
        )
        // 20 turns?
        val uniqueSpellOptions = listOf("Drain", "Shield", "Poison", "Recharge", "Magic Missile")
        val startSpells = listOf(
            "Shield",
            "Recharge",
            "Poison",
            "Shield",
            "Recharge",
            "Poison",
            "Shield",
            "Recharge",
            "Poison",
            "Shield",
            "Recharge",
            "Poison",
            "Shield",
            "Recharge"
        )
        val s = startSpells + buildSpellOptions(uniqueSpellOptions, 2)
        getPermutations(s, ::playGame)
    }

    var minMana = Int.MAX_VALUE

    private fun playGame(spells: List<String>) {
        val bossHP = 71
        val bossDamage = 10
        val hp = 50
        val mana = 500
        val boss = Player("Boss", bossHP, bossDamage, 0, 0, emptyList())
        val me = Player(
            "Me", hp, 0, 0, mana, listOf(
                MagicMissile(),
                Drain(),
                Shield(),
                Poison(),
                Recharge()
            )
        )
//        println(spells.toMutableList())
        val game = Game(me, boss, spells.toMutableList())
        val winner = game.play()
        if (winner == me) {
            if (game.manaUsed < minMana) {
                minMana = game.manaUsed
                println(spells)
                println(game.manaUsed)
            }
            // 2402 is too high
            // 2166 is too high
        }
    }

    private fun buildSpellOptions(uniqueSpellOptions: List<String>, size: Int): List<String> {
        val result = mutableListOf<String>()
        for (i in 0 until size) {
            result.addAll(uniqueSpellOptions)
        }
        return result
    }

    override fun solve2(lines: List<String>) {
    }
}

data class Player(
    val name: String,
    var hp: Int,
    var damage: Int,
    var armor: Int,
    var mana: Int,
    var spells: List<Spell>
) {
    fun getSpell(name: String): Spell {
        return spells.find { it.name == name }!!
    }

    override fun toString(): String {
        return "Player(name='$name', hp=$hp, damage=$damage, armor=$armor, mana=$mana)"
    }
}

class Game(private val me: Player, private val boss: Player, private val spells: MutableList<String>) {

    var manaUsed = 0

    fun play(): Player {
        return play(me, boss)
    }

    private fun play(attacker: Player, defender: Player): Player {
//        println("Turn: ${attacker.name}")
//        println(me)
//        println(boss)

        // First the effects
        for (spell in me.spells) {
            spell.effect(me, boss)
        }

        val winner = getWinner()
        if (winner != null) return winner

        // If attacker has damage, just hit!
        if (attacker.damage > 0) {
            val hp = maxOf(1, attacker.damage - defender.armor)
//            println("Attack with $hp damage")
            defender.hp -= hp
        }
        // Otherwise, do the mana play
        else {
            if (spells.isEmpty()) {
//                println("No more spells remaining")
                return boss
            }
            val spell = spells.removeAt(0)
//            println(spell)
            val mana = attacker.getSpell(spell).cast(me, boss)
            if (mana < 0) {
//                println("$spell could not be casted")
                return boss
            }
            manaUsed += mana
        }
        return play(defender, attacker)
    }

    private fun getWinner(): Player? {
        if (me.hp <= 0) {
            return boss
        }
        if (boss.hp <= 0) {
            return me
        }
        return null
    }
}

abstract class Spell(val name: String, var timer: Int = 0) {
    fun effect(me: Player, boss: Player) {
        if (timer > 0) {
            applyEffect(me, boss)
            timer--
            if (timer == 0) {
                endEffect(me, boss)
            }
        }
    }

    protected abstract fun applyEffect(me: Player, boss: Player)

    protected abstract fun endEffect(me: Player, boss: Player)

    abstract fun cast(me: Player, boss: Player): Int
}

class MagicMissile : Spell("Magic Missile") {
    override fun applyEffect(me: Player, boss: Player) {
        // No effect
    }

    override fun endEffect(me: Player, boss: Player) {
        // No effect
    }

    override fun cast(me: Player, boss: Player): Int {
        if (me.mana >= 53) {
            me.mana -= 53
            boss.hp -= 4
            return 53
        }
        return -1
    }
}

class Drain : Spell("Drain") {
    override fun applyEffect(me: Player, boss: Player) {
        // No effect
    }

    override fun endEffect(me: Player, boss: Player) {
        // No effect
    }

    override fun cast(me: Player, boss: Player): Int {
        if (me.mana >= 73) {
            me.mana -= 73
            me.hp += 2
            boss.hp -= 2
            return 73
        }
        return -1
    }
}

class Shield : Spell("Shield") {
    override fun applyEffect(me: Player, boss: Player) {
        // No repeated effect
    }

    override fun endEffect(me: Player, boss: Player) {
        // When the timer has ran out, reduce the armor with 7
        me.armor -= 7
    }

    override fun cast(me: Player, boss: Player): Int {
        if (timer == 0 && me.mana >= 113) {
            timer = 6
            me.mana -= 113
            me.armor += 7
            return 113
        }
        return -1
    }
}

class Poison : Spell("Poison") {
    override fun applyEffect(me: Player, boss: Player) {
        boss.hp -= 3
    }

    override fun endEffect(me: Player, boss: Player) {
        // No ending effect
    }

    override fun cast(me: Player, boss: Player): Int {
        if (timer == 0 && me.mana >= 173) {
            timer = 6
            me.mana -= 173
            return 173
        }
        return -1
    }
}

class Recharge : Spell("Recharge") {
    override fun applyEffect(me: Player, boss: Player) {
        me.mana += 101
    }

    override fun endEffect(me: Player, boss: Player) {
        // No ending effect
    }

    override fun cast(me: Player, boss: Player): Int {
        if (timer == 0 && me.mana >= 229) {
            timer = 5
            me.mana -= 229
            return 229
        }
        return -1
    }
}
