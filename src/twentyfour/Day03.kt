package twentyfour

import println
import readInput

fun main() {
    val input = readInput("twentyfour/Day03").joinToString()

    part1(input).println()
    part2(input).println()
}

private fun part1(input: String): Int {
    var result = 0

    for (i in input.indices) {
        val instruction = input.substring(i..<(i + 4).coerceIn(0, input.length))

        if (instruction == "mul(") {
            val stack = mutableListOf<Int>()
            val string = input.substring((i + 4).coerceIn(0, input.length))
            var number = ""

            for (j in string) {
                when {
                    j.isDigit() && number.length < 3 -> {
                        number += j
                    }

                    j == ',' -> {
                        stack.push(number.toInt())
                        number = ""
                        continue
                    }

                    j == ')' && stack.size == 1 -> {
                        result += stack.pop() * number.toInt()
                        break
                    }

                    else -> {
                        break
                    }
                }
            }
        }
    }

    return result
}

private fun part2(input: String): Int {

    var result = 0
    var flag = true

    for (i in input.indices) {
        val instruction = input.substring(i..<(i + 4).coerceIn(0, input.length))

        if (input.substring(i..<(i + 7).coerceIn(0, input.length)) == "don't()") {
            flag = false
        } else if (input.substring(i..<(i + 4).coerceIn(0, input.length)) == "do()") {
            flag = true
        }

        if (instruction == "mul(") {
            val stack = mutableListOf<Int>()
            val string = input.substring((i + 4).coerceIn(0, input.length))
            var number = ""

            for (j in string) {
                when {
                    j.isDigit() && number.length < 3 -> {
                        number += j
                    }

                    j == ',' -> {
                        stack.push(number.toInt())
                        number = ""
                        continue
                    }

                    j == ')' && stack.size == 1 && flag -> {
                        result += stack.pop() * number.toInt()
                        break
                    }

                    else -> {
                        break
                    }
                }
            }
        }
    }

    return result
}

private fun <T> MutableList<T>.pop(): T {
    return removeAt(lastIndex)
}

private fun <T> MutableList<T>.push(element: T) {
    return addFirst(element)
}

