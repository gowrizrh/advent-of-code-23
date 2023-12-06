val bySpaces = Regex("\\s+")

fun part1(input: List<String>) {
    val times = input.first().split(":").last().trim().split(bySpaces)
    val distances = input.last().split(":").last().trim().split(bySpaces)

    // equal number of races so times and distances size are always the same
    val numberOfRaces = times.indices

    (numberOfRaces)
        .map {
            println("Race $it: Time: ${times[it]}ms, Distance: ${distances[it]}mm")

            val time = times[it].toInt()
            val recordDistance = distances[it].toInt()
            val choices = 0..time

            choices
                .map { possible ->
                    // distance = speed x time
                    possible * (time - possible)
                }
                .filter { distance ->
                    distance > recordDistance
                }
                .size
        }
        .reduce { acc, i ->
            acc * i
        }
        .println()

}

fun part2(input: List<String>) {
    val time = input.first().split(":").last().trim().toInt()
    val recordDistance = input.last().split(":").last().trim().toULong()
    val choices = 9..time

    choices
        .map { possible ->
            // distance = speed x time
            possible.toULong() * (time.toULong() - possible.toULong())
        }
        .filter { distance ->
            distance > recordDistance
        }
        .size
        .println()

    return

}

fun main() {
    val input = readInput("Day06")

    part1(input)

    val inputTwo = readInput("Day06_2")
    part2(inputTwo)
}