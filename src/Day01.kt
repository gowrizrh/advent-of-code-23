fun main() {
    fun parse(line: String): Sequence<MatchResult> {
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

    fun cast(result: Sequence<MatchResult>): Int {
        val firstDigit = match(result.first().groupValues.last())
        val lastDigit = match(result.last().groupValues.last())
        return "$firstDigit$lastDigit".toInt()
    }

    val lines = readInput("Day01")

    val answer = lines.asSequence()
        .map { parse(it) }
        .map { cast(it) }
        .sum()

    println(answer)
}
