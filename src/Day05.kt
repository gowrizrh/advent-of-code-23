import kotlin.time.measureTime

val withMapName = Regex(" map:")
val withString = Regex("[a-z\\-]+")

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
    val seeds = input[0].split(": ").last().split(" ").map { it.toUInt() }
    input.removeFirst()

    val almanacMaps = mutableMapOf<String, MutableMap<String, MutableList<Pair<UInt, UInt>>>>()

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

        val source = almanac[1].toUInt()
        val destination = almanac[0].toUInt()
        val range = almanac[2].toUInt()

        val sourceEnd = source + range;
        val destinationEnd = destination + range;

        almanacMaps[currentMap]!!["source"]!!.add(Pair(source, sourceEnd))
        almanacMaps[currentMap]!!["destination"]!!.add(Pair(destination, destinationEnd))
    }

    val locations = mutableListOf<UInt>()

    val time = measureTime {
        for (seed in seeds) {
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

            locations.add(mappedValue)
        }
    }

    locations.toSortedSet().println()
    time.println()
}
