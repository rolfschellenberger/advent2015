package com.rolf.day18

import com.rolf.Day
import com.rolf.util.MatrixString
import com.rolf.util.Point
import com.rolf.util.splitLines

fun main() {
    Day18().run()
}

class Day18 : Day() {
    override fun getDay(): Int {
        return 18
    }

    override fun solve1(lines: List<String>) {
        val input = splitLines(lines)
        val grid = MatrixString.build(input)

        for (i in 0 until 100) {
            val (on, off) = getOnOffStates(grid)
            turnOn(grid, on)
            turnOff(grid, off)
        }

        println(grid.count("#"))
    }

    override fun solve2(lines: List<String>) {
        val input = splitLines(lines)
        val grid = MatrixString.build(input)
        val onPoints = setOf(
            Point(0, 0),
            Point(0, grid.height() - 1),
            Point(grid.width() - 1, 0),
            Point(grid.width() - 1, grid.height() - 1)
        )
        turnOn(grid, onPoints)

        for (i in 0 until 100) {
            val (on, off) = getOnOffStates(grid)
            turnOn(grid, on)
            turnOff(grid, off)
            turnOn(grid, onPoints)
        }

        println(grid.count("#"))
    }

    private fun getOnOffStates(grid: MatrixString): Pair<Set<Point>, Set<Point>> {
        val on = mutableSetOf<Point>()
        val off = mutableSetOf<Point>()

        for (point in grid.allPoints()) {
            val neighbours = grid.getNeighbours(point)
            val neighboursOn = neighbours
                .map { grid.get(it) }
                .count { it == "#" }
            when (grid.get(point)) {
                "#" -> {
                    if (neighboursOn == 2 || neighboursOn == 3) {
                        on.add(point)
                    } else {
                        off.add(point)
                    }
                }
                "." -> {
                    if (neighboursOn == 3) {
                        on.add(point)
                    } else {
                        off.add(point)
                    }
                }
            }
        }
        return Pair(on, off)
    }

    private fun turnOn(grid: MatrixString, points: Set<Point>) {
        for (point in points) {
            grid.set(point, "#")
        }
    }

    private fun turnOff(grid: MatrixString, points: Set<Point>) {
        for (point in points) {
            grid.set(point, ".")
        }
    }
}
