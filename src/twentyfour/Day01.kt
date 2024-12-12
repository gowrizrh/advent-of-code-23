package twentyfour

import println
import readInput
import java.util.PriorityQueue
import kotlin.math.abs

val regex = Regex("\\s{3}")

fun main() {
    part1().println()
    part2().println()
}

private fun part1(): Int {
    val left = PriorityQueue<Int>()
    val right = PriorityQueue<Int>()

    val lines = readInput("twentyfour/Day01")

    lines.forEach {
        val numbers = it.split(regex)
        val l = numbers.first().toInt()
        val r = numbers.last().toInt()

        left.add(l)
        right.add(r)
    }

    return lines.fold(0) { acc, _ ->
        acc + abs(right.poll() - left.poll())
    }
}

private fun part2(): Int {
    val lines = readInput("twentyfour/Day01")
    val appearances = mutableMapOf<Int, Int>()

    lines.forEach {
        val numbers = it.split(regex)
        val r = numbers.last().toInt()

        appearances[r] = appearances.getOrDefault(r, 0) + 1
    }

    return lines.fold(0) { acc, it ->
        val numbers = it.split(regex)
        val l = numbers.first().toInt()

        acc + (l * appearances.getOrDefault(l, 0))
    }
}