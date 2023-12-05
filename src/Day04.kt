import kotlin.math.pow

val regex = Regex("\\s+")

fun main() {

    val input = readInput("Day04")
    val scratchcards = MutableList(input.size) { 1 }

    // Part 1, Part 2
    input
        .asSequence()
        .map { split(it) }
        .map { match(it) }
        .mapIndexed { index, matches ->
            val wins = matches.size
            val copies = 0..<scratchcards[index]

            copies.forEach { _ ->
                (index..<index + wins)
                    .forEach {
                        scratchcards[it + 1] = scratchcards[it + 1] + 1
                    }
            }

            matches
        }
        .map { score(it) }
        .sum()
        .println()
        .also {
            scratchcards.sum().println()
        }

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