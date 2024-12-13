package twentythree

import readInput
import kotlin.math.abs

fun main() {
    println("--- Day 11: Cosmic Expansion ---")
    val input = readInput("Day11")

    val universe = Instrument(input)
    println("Galaxy pairs found: ${universe.galaxies.size}")

    // Part 1
    universe.expansion = 1
    universe
        .galaxies
        .sumOf { (start, end) -> universe.manhattan(start, end) }
        .also(::println)

    // Part 2
    universe.expansion = 999999
    universe
        .galaxies
        .sumOf { (start, end) -> universe.manhattan(start, end) }
        .also(::println)

    return
}

class Instrument(input: List<String>, var expansion: Long = 1) {
    private val cosmicRows = mutableSetOf<Int>()
    private val cosmicColumns = mutableSetOf<Int>()
    var galaxies = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

    init {
        for ((x, line) in input.withIndex()) {
            if (line.all { it == '.' }) {
                cosmicRows.add(x)
            }
        }

        for (y in input[0].indices) {
            val list = mutableListOf<Char>()
            for (x in input.indices) {
                list.add(input[x][y])
            }

            if (list.all { it == '.' }) {
                cosmicColumns.add(y)
            }
        }

        val galaxy = mutableListOf<Pair<Int, Int>>()

        for ((x, line) in input.withIndex()) {
            for ((y, token) in line.withIndex()) {
                if (token == '#') {
                    galaxy.add(Pair(x, y))
                }
            }
        }

        for (i in 0..<galaxy.size) {
            for (j in i + 1..<galaxy.size) {
                galaxies.add(Pair(galaxy[i], galaxy[j]))
            }
        }

        println("Rows expanded: $cosmicRows")
        println("Columns expanded: $cosmicColumns")
    }

    fun manhattan(start: Pair<Int, Int>, end: Pair<Int, Int>): Long {
        val pairOneXOffset = cosmicRows.filter { it < start.first }.size * expansion
        val pairOneYOffset = cosmicColumns.filter { it < start.second }.size * expansion

        val pairTwoXOffset = cosmicRows.filter { it < end.first }.size * expansion
        val pairTwoYOffset = cosmicColumns.filter { it < end.second }.size * expansion

        val dx = abs((start.first + pairOneXOffset) - (end.first + pairTwoXOffset))
        val dy = abs((start.second + pairOneYOffset) - (end.second + pairTwoYOffset))

        return dx + dy
    }
}
