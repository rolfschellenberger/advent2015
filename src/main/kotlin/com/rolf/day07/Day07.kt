package com.rolf.day07

import com.rolf.Day
import com.rolf.util.Binary
import com.rolf.util.isNumeric

fun main() {
    Day07().run()
}

class Day07 : Day() {
    override fun getDay(): Int {
        return 7
    }

    override fun solve1(lines: List<String>) {
        val wires = mutableMapOf<String, Binary>()
        val actions = parseActions(lines)

        runActions(actions, wires)
        println(wires["a"]?.value)
    }

    override fun solve2(lines: List<String>) {
        val wires = mutableMapOf<String, Binary>()
        val actions = parseActions(lines)

        // Overwrite b
        for (action in actions) {
            if (action is Assign && action.to == "b") {
                action.from = "956"
            }
        }

        runActions(actions, wires)
        println(wires["a"]?.value)
    }

    private fun parseActions(lines: List<String>): MutableList<Action> {
        val actions = mutableListOf<Action>()
        for (line in lines) {
            val (actionPart, target) = line.split(" -> ")
            val params = actionPart.split(" ")
            actions.add(parseAction(target, params))
        }
        return actions
    }

    private fun parseAction(target: String, params: List<String>): Action {
        if (params.contains("AND")) {
            return And(params[0], params[2], target)
        }
        if (params.contains("OR")) {
            return Or(params[0], params[2], target)
        }
        if (params.contains("NOT")) {
            return Not(params[1], target)
        }
        if (params.contains("LSHIFT")) {
            return LeftShift(params[0], params[2], target)
        }
        if (params.contains("RSHIFT")) {
            return RightShift(params[0], params[2], target)
        }
        return Assign(params[0], target)
    }

    private fun runActions(
        actions: MutableList<Action>,
        wires: MutableMap<String, Binary>
    ) {
        while (actions.isNotEmpty()) {
            val iterator = actions.iterator()
            while (iterator.hasNext()) {
                val action = iterator.next()
                if (action.run(wires)) {
                    iterator.remove()
                }
            }
        }
    }
}

abstract class Action {
    abstract fun run(wires: MutableMap<String, Binary>): Boolean

    fun getValue(field: String, wires: Map<String, Binary>): Binary? {
        if (field.isNumeric()) {
            return Binary(field.toLong())
        }
        return wires[field]
    }
}

data class Assign(var from: String, val to: String) : Action() {
    override fun run(wires: MutableMap<String, Binary>): Boolean {
        val value = getValue(from, wires)
        if (value != null) {
            wires[to] = value
            return true
        }
        return false
    }
}

data class And(private val one: String, private val two: String, private val to: String) : Action() {
    override fun run(wires: MutableMap<String, Binary>): Boolean {
        val oneValue = getValue(one, wires)
        val twoValue = getValue(two, wires)
        if (oneValue != null && twoValue != null) {
            wires[to] = oneValue.copy().and(twoValue.value)
            return true
        }
        return false
    }
}

data class Or(private val one: String, private val two: String, private val to: String) : Action() {
    override fun run(wires: MutableMap<String, Binary>): Boolean {
        val oneValue = getValue(one, wires)
        val twoValue = getValue(two, wires)
        if (oneValue != null && twoValue != null) {
            wires[to] = oneValue.copy().or(twoValue.value)
            return true
        }
        return false
    }
}

data class Not(private val from: String, private val to: String) : Action() {
    override fun run(wires: MutableMap<String, Binary>): Boolean {
        val value = getValue(from, wires)
        if (value != null) {
            wires[to] = value.copy().not()
            return true
        }
        return false
    }
}

data class LeftShift(private val from: String, private val shift: String, private val to: String) : Action() {
    override fun run(wires: MutableMap<String, Binary>): Boolean {
        val value = getValue(from, wires)
        val shiftValue = getValue(shift, wires)
        if (value != null && shiftValue != null) {
            wires[to] = value.copy().shiftLeft(shiftValue.value.toInt())
            return true
        }
        return false
    }
}

data class RightShift(private val from: String, private val shift: String, private val to: String) : Action() {
    override fun run(wires: MutableMap<String, Binary>): Boolean {
        val value = getValue(from, wires)
        val shiftValue = getValue(shift, wires)
        if (value != null && shiftValue != null) {
            wires[to] = value.copy().shiftRight(shiftValue.value.toInt())
            return true
        }
        return false
    }
}
