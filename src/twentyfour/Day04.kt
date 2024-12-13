package twentyfour

import println
import readInput

fun main() {
    val input = readInput("twentyfour/Day04")

    part1(input).println()
    part2(input).println()
}

private fun part1(input: List<String>): Int {
    val grid: Array<CharArray> = Array(input.size) { CharArray(input.size) }
    var count = 0

    for (i in input.indices) {
        val row = input[i].toCharArray()
        grid[i] = row
    }

    for (y in grid.indices) {
        for (x in grid[y].indices) {
            when (grid[y][x]) {
                'X' -> {
                    val word = StringBuilder()

                    val rightIndex = kotlin.math.min(x + 3, grid[y].lastIndex)
                    val leftIndex = kotlin.math.max(0, x - 3)

                    // Right: y, x + 1
                    var slice = grid[y].slice(x..rightIndex).joinToString("")
                    if (slice == "XMAS") {
                        count++
                    }

                    // Left: y, x - 1
                    slice = grid[y].slice(leftIndex..x).asReversed().joinToString("")

                    if (slice == "XMAS") {
                        count++
                    }

                    // Down: y + 1
                    for (i in 0..<4) {
                        if (y + i > grid[y].lastIndex) {
                            break
                        }

                        word.append(grid[y + i][x])
                    }
                    if (word.toString() == "XMAS") {
                        count++
                    }
                    word.clear()

                    // Down left: y + 1, x - 1
                    for (i in 0..<4) {
                        if (x - i < 0 || y + i > grid[y].lastIndex) {
                            break
                        }
                        word.append(grid[y + i][x - i])

                    }
                    if (word.toString() == "XMAS") {
                        count++
                    }
                    word.clear()

                    // Down right: y + 1, x + 1
                    for (i in 0..<4) {
                        if (y + i > grid[y].lastIndex || x + i > grid[y].lastIndex) {
                            break
                        }
                        word.append(grid[y + i][x + i])
                    }
                    if (word.toString() == "XMAS") {
                        count++
                    }
                    word.clear()

                    // Up: y - 1
                    for (i in 0..<4) {
                        if (y - i < 0) {
                            break
                        }

                        word.append(grid[y - i][x])
                    }
                    if (word.toString() == "XMAS") {
                        count++
                    }
                    word.clear()

                    // Up left: y - 1, x - 1
                    for (i in 0..<4) {
                        if (y - i < 0 || x - i < 0) {
                            break
                        }

                        word.append(grid[y - i][x - i])
                    }
                    if (word.toString() == "XMAS") {
                        count++
                    }
                    word.clear()

                    // Up right: y - 1, x + 1
                    for (i in 0..<4) {
                        if (y - i < 0 || x + i > grid[y].lastIndex) {
                            break
                        }

                        word.append(grid[y - i][x + i])
                    }
                    if (word.toString() == "XMAS") {
                        count++
                    }
                    word.clear()
                }
            }
        }
    }

    return count
}

private fun part2(input: List<String>): Int {
    val grid: Array<CharArray> = Array(input.size) { CharArray(input.size) }
    var count = 0
    val validPairs = setOf(Pair('S', 'M'), Pair('M', 'S'))

    for (i in input.indices) {
        val row = input[i].toCharArray()
        grid[i] = row
    }

    for (y in grid.indices) {
        for (x in grid[y].indices) {
            when (grid[y][x]) {
                'A' -> {
                    if (y - 1 < 0 || x - 1 < 0 || y + 1 > input.lastIndex || x + 1 > input.lastIndex) {
                        print("* ")
                        continue
                    }

                    val left = Pair(grid[y - 1][x - 1], grid[y + 1][x + 1])
                    val right = Pair(grid[y - 1][x + 1], grid[y + 1][x - 1])

                    if (left in validPairs && right in validPairs) {
                        print("A ")
                        count++
                    } else {
                        print(". ")
                    }
                }

                else -> {
                    print(". ")
                }
            }
        }
        println()
    }

    return count
}