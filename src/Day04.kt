import kotlin.math.pow

val regex = Regex("\\s+")

fun main() {
    val input = readInput("Day04")

    // Part 1
    input
        .map { split(it) }
        .map { match(it) }
        .map { score(it) }
        .sumOf { it }
        .println()
}

fun split(card: String): List<String> {
    return card.split(": ").last().split(" | ")
}

fun match(set: List<String>): List<String> {
    val winningNumbers = set.first().trim().split(regex).toHashSet()
    val inHand = set.last().trim().split(regex).toHashSet()

    return inHand.filter { number ->
        winningNumbers.contains(number)
    }
}

fun score(match: List<String>): Int {
    val size = match.size - 1

    return 2.0.pow(size).toInt()
}