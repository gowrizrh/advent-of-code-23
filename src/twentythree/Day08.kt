package twentythree

import readInput

val networkTry = Regex("(?<node>[A-Z]+) = \\((?<left>[A-Z]+), (?<right>[A-Z]+)\\)")

enum class Direction {
    L, R
}

fun main() {
    println("--- Day 8: Haunted Wasteland ---")
    val input = readInput("Day08")
    val instructions = input.first()

//    input.removeFirst()
//    input.removeFirst()

    val startNode = "AAA"

    val network = input
        .fold(mutableMapOf<String, Pair<String, String>>()) { acc, instruction ->
            val matches = networkTry.find(instruction)!!.groups
            val node = matches["node"]!!.value
            val left = matches["left"]!!.value
            val right = matches["right"]!!.value

            acc[node] = Pair(left, right)

            acc
        }


    var reached = false
    var stepCount = 1
    var currentNode = startNode

    while (!reached) {
        for (step in instructions) {
            val node = when (Direction.valueOf(step.toString())) {
                Direction.L -> network[currentNode]!!.first
                Direction.R -> network[currentNode]!!.second
            }

            if (node == "ZZZ") {
                println("Reached end in $stepCount steps")
                reached = true
            } else {
                currentNode = node
                stepCount++
            }
        }
    }
}