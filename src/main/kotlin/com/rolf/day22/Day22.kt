package com.rolf.day22

import com.rolf.Day

fun main() {
    Day22().run()
}

class Day22 : Day() {
    override fun getDay(): Int {
        return 22
    }

    private var minMana = Int.MAX_VALUE

    override fun solve1(lines: List<String>) {
        println(solve(false))
    }

    override fun solve2(lines: List<String>) {
        println(solve(true))
    }

    private fun solve(hard: Boolean): Int {
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

        val game = Game(me, boss, hard)
        minMana = Int.MAX_VALUE
        turn(game)
        return minMana
    }

    private fun turn(game: Game) {
        if (game.manaUsed < minMana) {
            val uniqueSpellOptions = listOf("Drain", "Shield", "Poison", "Recharge", "Magic Missile")
            for (spell in uniqueSpellOptions) {
                val gameCopy = game.deepCopy()
                val winner = gameCopy.turn(spell)
                val winner2 = gameCopy.turn(null)
                if (winner == null && winner2 == null) {
                    // No winner? Continue
                    turn(gameCopy)
                } else {
                    // Someone won
                    if (winner == gameCopy.me || (winner != gameCopy.boss && winner2 == gameCopy.me)) {
                        minMana = minOf(minMana, gameCopy.manaUsed)
                    }
                }
            }
        }
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

    fun deepCopy(): Player {
        val copy = this.copy()
        copy.spells = spells.map { it.deepCopy() }
        return copy
    }
}

class Game(
    val me: Player,
    val boss: Player,
    private val hard: Boolean,
    var manaUsed: Int = 0,
    var turn: Int = 0,
    private val usedSpells: MutableList<String> = mutableListOf()
) {

    fun turn(spell: String?): Player? {
        val winner = if (turn % 2 == 0) {
            turn(me, boss, spell)
        } else {
            turn(boss, me, spell)
        }
        turn++
        return winner
    }

    private fun turn(attacker: Player, defender: Player, spellToCast: String?): Player? {
        // First lose 1 health
        if (hard && attacker == me) {
            me.hp -= 1
            val winner1 = getWinner()
            if (winner1 != null) return winner1
        }

        // First the effects
        for (spell in me.spells) {
            spell.effect(me, boss)
        }

        val winner = getWinner()
        if (winner != null) return winner

        // If attacker has damage, just hit!
        if (attacker.damage > 0) {
            val hp = maxOf(1, attacker.damage - defender.armor)
            defender.hp -= hp
        }
        // Otherwise, do the mana play
        else {
            val mana = attacker.getSpell(spellToCast!!).cast(me, boss)
            usedSpells.add(spellToCast)
            if (mana < 0) {
                return boss
            }
            manaUsed += mana
        }

        return getWinner()
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

    fun deepCopy(): Game {
        return Game(me.deepCopy(), boss.deepCopy(), hard, manaUsed, turn, usedSpells.toMutableList())
    }

    override fun toString(): String {
        return "Game(me=$me, boss=$boss, manaUsed=$manaUsed, turn=$turn, usedSpells=$usedSpells)"
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

    abstract fun deepCopy(): Spell
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

    override fun deepCopy(): Spell {
        val spell = MagicMissile()
        spell.timer = timer
        return spell
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

    override fun deepCopy(): Spell {
        val spell = Drain()
        spell.timer = timer
        return spell
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

    override fun deepCopy(): Spell {
        val spell = Shield()
        spell.timer = timer
        return spell
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

    override fun deepCopy(): Spell {
        val spell = Poison()
        spell.timer = timer
        return spell
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

    override fun deepCopy(): Spell {
        val spell = Recharge()
        spell.timer = timer
        return spell
    }
}
