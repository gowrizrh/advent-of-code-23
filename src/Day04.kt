import kotlin.math.pow

fun main() {
    val input = readInput("Day04")

    // Part 1
    input
        .map { card ->
            card.split(": ").last().split(" | ")
        }
        .map { set ->

            val winningNumbers = set.first().trim().split(Regex("\\s+")).toHashSet()
            val inHand = set.last().trim().split(Regex("\\s+")).toHashSet()

            inHand.filter { number ->
                winningNumbers.contains(number)
            }
        }
        .map { match ->
            val size = match.size - 1

            2.toDouble().pow(size).toInt()
        }
        .sumOf { it }
        .println()
}
