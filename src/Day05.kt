import kotlin.time.measureTime

val withMapName = Regex(" map:")
val withString = Regex("[a-z\\-]+")
val withSeedRanges = Regex("(?<start>\\d+) (?<range>\\d+)")

val pipeline = listOf(
    "seed-to-soil",
    "soil-to-fertilizer",
    "fertilizer-to-water",
    "water-to-light",
    "light-to-temperature",
    "temperature-to-humidity",
    "humidity-to-location",
)

fun main() {
    val input = readInput("Day05")
    val seeds = input[0].split(": ").last()
    input.removeFirst()

    val almanacMaps = mutableMapOf<String, MutableMap<String, MutableList<Pair<ULong, ULong>>>>()

    var currentMap = ""

    // TODO: functional style
    for (line in input) {
        if (line == "") continue

        val mapName = line.split(withMapName).first()

        if (withString.matches(mapName)) {
            almanacMaps.putIfAbsent(
                mapName,
                mutableMapOf("source" to mutableListOf(), "destination" to mutableListOf())
            )
            currentMap = mapName
            continue
        }

        val almanac = mapName.split(" ")

        val source = almanac[1].toULong()
        val destination = almanac[0].toULong()
        val range = almanac[2].toULong()

        val sourceEnd = source + range;
        val destinationEnd = destination + range;

        almanacMaps[currentMap]!!["source"]!!.add(Pair(source, sourceEnd))
        almanacMaps[currentMap]!!["destination"]!!.add(Pair(destination, destinationEnd))
    }

    val locations = mutableListOf<ULong>()
    var lowest: ULong = ULong.MAX_VALUE

    val time = measureTime {
        for (seedPair in withSeedRanges.findAll(seeds)) {
            val seedStart = seedPair.groups["start"]!!.value.toULong()
            val range = seedPair.groups["range"]!!.value.toULong()
            val seedEnd = seedStart + range

            (seedStart..<seedEnd)
                .forEach { seed ->
                    var mappedValue = seed

                    for (map in pipeline) {
                        val mapMatch = almanacMaps[map]!!["source"]!!.indexOfFirst { (first, second) ->
                            mappedValue in first..<second
                        }

                        if (mapMatch >= 0) {
                            val source = almanacMaps[map]!!["source"]!!
                                .elementAt(mapMatch)

                            val destination = almanacMaps[map]!!["destination"]!!
                                .elementAt(mapMatch)

                            val difference = mappedValue - source.first

                            mappedValue = (destination.first + difference)
                        }
                    }

                    if (mappedValue < lowest) {
                        lowest = mappedValue
                    }
                }
        }
    }

    lowest.println()
    time.println()
}
