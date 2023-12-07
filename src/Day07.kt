val power = mapOf(
    'A' to 14,
    'K' to 13,
    'Q' to 12,
    'J' to 11,
    'T' to 10,
    '9' to 9,
    '8' to 8,
    '7' to 7,
    '6' to 6,
    '5' to 5,
    '4' to 4,
    '3' to 3,
    '2' to 2,
    'J' to 1
)

fun main() {
    val input = readInput("Day07")

    input
        .map { game ->
            game.split(" ")
        }
        .map { game ->
            val cards = game.first().map { it }
            val bid = game.last().toInt()

            val type = game.first()
                .groupingBy { it }
                .eachCount()
                .map { it.value to it.key }
                .groupingBy { it.first }
                .eachCount()
                .toSortedMap()
                .let { type ->
                    when (type) {
                        mapOf(1 to 5) -> Type.HIGHCARD
                        mapOf(1 to 3, 2 to 1) -> Type.ONEPAIR
                        mapOf(1 to 1, 2 to 2) -> Type.TWOPAIR
                        mapOf(1 to 2, 3 to 1) -> Type.THREEOFAKIND
                        mapOf(2 to 1, 3 to 1) -> Type.FULLHOUSE
                        mapOf(1 to 1, 4 to 1) -> Type.FOUROFAKIND
                        mapOf(5 to 1) -> Type.FIVEOFAKIND
                        else -> null
                    }
                }

            Hand(cards, bid, type!!)
        }
        .sorted()
        .mapIndexed { index, hand ->
            hand.bid * (index + 1)
        }
        .sum()
        .also(::println)

}


data class Hand(val cards: List<Char>, val bid: Int, val type: Type) : Comparable<Hand> {
    override fun compareTo(other: Hand): Int {
        return if (type.strength < other.type.strength) {
            -1
        } else if (type.strength > other.type.strength) {
            1
        } else {
            for ((index, card) in cards.withIndex()) {
                return if (power[card]!! < power[other.cards[index]]!!) {
                    -1
                } else if (power[card]!! > power[other.cards[index]]!!) {
                    1
                } else {
                    continue
                }
            }
            0
        }
    }
}

enum class Type(val strength: Int) {
    HIGHCARD(1),
    ONEPAIR(2),
    TWOPAIR(3),
    THREEOFAKIND(4),
    FULLHOUSE(5),
    FOUROFAKIND(6),
    FIVEOFAKIND(7),
}