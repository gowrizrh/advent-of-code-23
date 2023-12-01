fun main() {
    val debug = false;

    fun parse(line: String): Sequence<MatchResult> {
        // Lookahead search
        val reg = Regex("(?=(one|two|three|four|five|six|seven|eight|nine|[1-9]))")
        return reg.findAll(line)
    }

    fun match(token: String): String {
        return when (token) {
            "one" -> {
                "1"
            }

            "two" -> {
                "2"
            }

            "three" -> {
                "3"
            }

            "four" -> {
                "4"
            }

            "five" -> {
                "5"
            }

            "six" -> {
                "6"
            }

            "seven" -> {
                "7"
            }

            "eight" -> {
                "8"
            }

            "nine" -> {
                "9"
            }

            else -> {
                token
            }
        }
    }

    val lines = readInput("Day01")

    val numbers = mutableListOf<Int>()

    for (line in lines) {
        val matches = parse(line)
        val firstDigit = match(matches.first().groupValues.last())
        val lastDigit = match(matches.last().groupValues.last())
        val number = "$firstDigit$lastDigit".toInt()

        if (debug) number.println()

        numbers.add(number)
    }

    println(numbers.sumOf { it })
}
