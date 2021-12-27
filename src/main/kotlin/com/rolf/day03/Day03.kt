package com.rolf.day03

import com.rolf.Day
import com.rolf.util.MatrixInt
import com.rolf.util.Point
import com.rolf.util.splitLine

fun main() {
    Day03().run()
}

class Day03 : Day() {
    override fun getDay(): Int {
        return 3
    }

    override fun solve1(lines: List<String>) {
        val grid = MatrixInt.buildDefault(1000, 1000, 0)
        val start = Point(500, 500)
        val directions = splitLine(lines[0], chunkSize = 1)
        move(grid, start, directions)
        println(grid.allElements().filter { it > 0 }.count())
    }

    private fun move(grid: MatrixInt, start: Point, directions: List<String>) {
        deliver(grid, start)

        // Move to the next positions
        var position = start
        for (direction in directions) {
            val newPosition = moveInDirection(position, direction, grid)
            deliver(grid, newPosition)
            position = newPosition
        }
    }

    private fun moveInDirection(location: Point, direction: String, grid: MatrixInt): Point {
        return when (direction) {
            ">" -> grid.getRight(location)
            "<" -> grid.getLeft(location)
            "^" -> grid.getUp(location)
            "v" -> grid.getDown(location)
            else -> throw Exception("Unknown direction: $direction")
        }!!
    }

    private fun deliver(grid: MatrixInt, point: Point) {
        grid.set(point, grid.get(point) + 1)
    }

    override fun solve2(lines: List<String>) {
        val grid = MatrixInt.buildDefault(1000, 1000, 0)
        val santa = Point(500, 500)
        val robot = Point(500, 500)
        val directions = splitLine(lines[0], chunkSize = 1)
        val groupedDirections =
            directions.mapIndexed { index, s -> index to s }.groupBy({ it.first % 2 }, { it.second })
        val santaDirections = groupedDirections[0]!!
        val robotDirections = groupedDirections[1]!!

        move(grid, santa, santaDirections)
        move(grid, robot, robotDirections)
        println(grid.allElements().filter { it > 0 }.count())
    }

    private fun getDirections(directions: List<String>, modulo: Int, moduloResult: Int): List<String> {
        return directions.filterIndexed { index, _ -> index % modulo == moduloResult }
    }
}
