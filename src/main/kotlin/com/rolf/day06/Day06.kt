package com.rolf.day06

import com.rolf.Day
import com.rolf.util.MatrixInt
import com.rolf.util.Point
import com.rolf.util.splitLine

fun main() {
    Day06().run()
}

class Day06 : Day() {
    override fun getDay(): Int {
        return 6
    }

    override fun solve1(lines: List<String>) {
        val grid = MatrixInt.buildDefault(1000, 1000, 0)
        val instructions = parseInstruction(lines)

        for (instruction in instructions) {
            val area = grid.getArea(instruction.from, instruction.to)
            doAction1(instruction, area, grid)
        }

        println(grid.count(1))
    }

    private fun doAction1(instruction: Instruction, area: List<Point>, grid: MatrixInt) {
        when (instruction.action) {
            "on" -> area.forEach { grid.set(it, 1) }
            "off" -> area.forEach { grid.set(it, 0) }
            "toggle" -> area.forEach { grid.set(it, (grid.get(it) + 1) % 2) }
        }
    }

    override fun solve2(lines: List<String>) {
        val grid = MatrixInt.buildDefault(1000, 1000, 0)
        val instructions = parseInstruction(lines)

        for (instruction in instructions) {
            val area = grid.getArea(instruction.from, instruction.to)
            doAction2(instruction, area, grid)
        }

        println(grid.allElements().sum())
    }

    private fun doAction2(instruction: Instruction, area: List<Point>, grid: MatrixInt) {
        when (instruction.action) {
            "on" -> area.forEach { grid.set(it, grid.get(it) + 1) }
            "off" -> area.forEach { grid.set(it, maxOf(0, grid.get(it) - 1)) }
            "toggle" -> area.forEach { grid.set(it, grid.get(it) + 2) }
        }
    }

    private fun parseInstruction(lines: List<String>): List<Instruction> {
        val instructions = mutableListOf<Instruction>()
        for (line in lines) {
            val (action, point1, _, point2) = splitLine(line.removePrefix("turn "), " ")
            val from = parsePoint(point1)
            val to = parsePoint(point2)
            val instruction = Instruction(from, to, action)
            instructions.add(instruction)
        }
        return instructions
    }

    private fun parsePoint(point: String): Point {
        val (x, y) = point.split(",").map { it.toInt() }
        return Point(x, y)
    }
}

data class Instruction(val from: Point, val to: Point, val action: String)
