package twentythree

import readInput

fun main() {
    val observations = readInput("Day09").map { it.split(" ").map { it.toInt() } }

    // Part 1
    observations
        .sumOf { analyse(it) }
        .also(::println)

    // Part 2
    observations
        .map { it.reversed() }
        .sumOf { analyse(it) }
        .also(::println)
}

fun analyse(values: List<Int>): Int {
    if (values.all { it == 0 }) return 0

    val diff = analyse(values.windowed(2).map {
        it.reduce { acc, s -> s - acc }
    })

    return values.last() + diff
}