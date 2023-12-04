val gameId = Regex("Game (?<id>\\d+):")

fun main() {
    val lines = readInput("Day02")

    // Part 1
    lines.filter {
        val game = it.split(": ").last()

        game.split("; ")
            .map { set ->
                set.split(", ")
                    .fold(mutableMapOf<String, Int>()) { acc, s ->
                        val split = s.split(" ")
                        acc.merge(split.last(), split.first().toInt(), Int::plus)
                        acc
                    }
            }
            .fold(mutableListOf<Boolean>()) { acc, coloursMap ->

                val red = coloursMap.getOrDefault("red", 0)
                val green = coloursMap.getOrDefault("green", 0)
                val blue = coloursMap.getOrDefault("blue", 0)

                acc.add(red <= 12 && green <= 13 && blue <= 14)

                acc
            }.reduce { acc, b ->
                if (!acc) false else if (b) true else false
            }
    }
        .map { gameId.find(it)?.groups?.get("id")?.value?.toInt() ?: 0 }
        .sumOf { it }
        .println()

    // Part 2
}

