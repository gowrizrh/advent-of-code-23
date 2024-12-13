package twentythree

import println
import readInput
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.time.measureTime

val bySpaces = Regex("\\s+")

fun part1(input: List<String>) {
    val times = input.first().split(":").last().trim().split(bySpaces)
    val distances = input.last().split(":").last().trim().split(bySpaces)

    // equal number of races so just assume times and distances size are the same
    val races = times.indices

    races
        .map {
            val time = times[it].toInt()
            val recordDistance = distances[it].toInt()
            val choices = 0..time

            choices
                .map { possible ->
                    // distance = speed x time
                    possible * (time - possible)
                }
                .filter { distance ->
                    distance > recordDistance
                }
                .size
        }
        .reduce { acc, i ->
            acc * i
        }
        .println()

}

fun part2(input: List<String>) {
    val time = input.first().split(":").last().trim().toFloat()
    val recordDistance = input.last().split(":").last().trim().toDouble()

    val a = time - sqrt(time.pow(2) - 4 * recordDistance)
    val b = time + sqrt(time.pow(2) - 4 * recordDistance)

    val lowerBound = floor(a / 2.0)
    val upperBound = ceil(b / 2.0)

    (upperBound - lowerBound - 1.0)
        .toInt().println()

    return
}

fun main() {
    println("--- Day 6: Wait For It ---")
    val input = readInput("Day06")
    val inputTwo = readInput("Day06_2")

    val time = measureTime {
        part1(input)

        part2(inputTwo)
    }

    time.println()
}
