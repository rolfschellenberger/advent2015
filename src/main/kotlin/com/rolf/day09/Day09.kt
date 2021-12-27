package com.rolf.day09

import com.rolf.Day
import com.rolf.util.EdgeType
import com.rolf.util.Graph
import com.rolf.util.Vertex

fun main() {
    Day09().run()
}

class Day09 : Day() {
    override fun getDay(): Int {
        return 9
    }

    override fun solve1(lines: List<String>) {
        val graph = buildGraph(lines)
        val (_, weight) = graph.lowestPathAndWeightVisitAll()
        println(weight.toInt())
    }

    override fun solve2(lines: List<String>) {
        val graph = buildGraph(lines)
        val (_, weight) = graph.highestPathAndWeightVisitAll()
        println(weight.toInt())
    }

    private fun buildGraph(lines: List<String>): Graph<Int> {
        val graph = Graph<Int>()
        for (line in lines) {
            val (fromTo, dist) = line.split(" = ")
            val (from, to) = fromTo.split(" to ")
            val distance = dist.toDouble()
            graph.addVertex(Vertex(from))
            graph.addVertex(Vertex(to))
            graph.addEdge(from, to, EdgeType.UNDIRECTED, distance)
        }
        return graph
    }
}
