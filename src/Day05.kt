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
    val seeds = input[0].split(": ").last().split(" ").map { it.toInt() }
//    seeds.println()
    input.removeFirst()

    val almanacMaps = mutableMapOf<String, MutableMap<String, MutableList<Pair<Int, Int>>>>()

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
//            mapName.println()
            currentMap = mapName
            continue
        }

        val almanac = mapName.split(" ")

        val source = almanac[1].toInt()
        val destination = almanac[0].toInt()
        val range = almanac[2].toInt()

        val sourceEnd = source + range;
        val destinationEnd = destination + range;

        almanacMaps[currentMap]!!["source"]!!.add(Pair(source, sourceEnd))
        almanacMaps[currentMap]!!["destination"]!!.add(Pair(destination, destinationEnd))
    }


    // WIP
    seeds
        .map { item ->

            val map = pipeline[0]

            almanacMaps[map]!!["source"]!!.foldIndexed(0) { i, _, pair ->
                if (item >= pair.first && item < pair.second) {
                    val difference = item - pair.first
                    val destinationRangeStart = almanacMaps[map]!!["destination"]!![i].first

                    destinationRangeStart + difference
                } else {
                    item
                }
            }
        }
        .also { println(it) }
        .map { item ->

            val map = pipeline[1]

            almanacMaps[map]!!["source"]!!.foldIndexed(0) { i, _, pair ->
                if (item >= pair.first && item < pair.second) {
                    val difference = item - pair.first
                    val destinationRangeStart = almanacMaps[map]!!["destination"]!![i].first

                    destinationRangeStart + difference
                } else {
                    item
                }
            }
        }
        .also { println(it) }
        .map { item ->

            val map = pipeline[2]

            almanacMaps[map]!!["source"]!!
                .foldIndexed(0) { i, _, pair ->
                if (item >= pair.first && item < pair.second) {
                    val difference = item - pair.first
                    val destinationRangeStart = almanacMaps[map]!!["destination"]!![i].first

                    destinationRangeStart + difference
                } else {
                    item
                }
            }
        }.println()


}