import kotlin.math.abs

fun main() {
    println("\"--- Day 11: Cosmic Expansion ---\"")
    val input = readInput("Day11")

    val universe = Image(input.toMutableList())
    val galaxies = universe.galaxies()
    println("Galaxy pairs found: ${galaxies.size}")

    galaxies
        .sumOf { manhattan(it.first, it.second) }
        .also { println(it) }

    return
}

fun manhattan(start: Space, end: Space): Int {
    val dx: Int = abs(start.x - end.x)
    val dy: Int = abs(start.y - end.y)

    return dx + dy
}

class Image(input: MutableList<String>) {
    private val rows = mutableListOf<MutableList<Space>>()

    init {
        val cosmicRows = mutableSetOf<Int>()
        val cosmicColumns = mutableSetOf<Int>()

        for ((x, line) in input.withIndex()) {
            if (line.all { it == '.' }) {
                cosmicRows.add(x)
            }
        }

        for (y in input[0].indices) {
            val list = mutableListOf<Char>()
            for (x in input.indices) {
                list.add(input[x][y])
            }

            if (list.all { it == '.' }) {
                cosmicColumns.add(y)
            }
        }

        for ((rowOffset, row) in cosmicRows.withIndex()) {
            input.add(row + rowOffset, String(input[row + rowOffset].toCharArray()))
        }

        for ((index, line) in input.withIndex()) {
            val stringBuilder = StringBuilder(line)

            for ((columnsOffset, column) in cosmicColumns.withIndex()) {

                stringBuilder.insert(column + columnsOffset, '.')
            }

            input[index] = stringBuilder.toString()
        }

        for ((x, line) in input.withIndex()) {
            val column = mutableListOf<Space>()

            for ((y, type) in line.withIndex()) {
                column.add(Space(x, y, type))
            }

            rows.add(column)
        }

        println("Rows expanded: $cosmicRows")
        println("Columns expanded: $cosmicColumns")
    }

    fun galaxies(): List<Pair<Space, Space>> {
        val pairs = mutableListOf<Pair<Space, Space>>()
        val galaxy = mutableListOf<Space>()

        for (x in 0..<rows.size) {
            for (y in 0..<rows[x].size) {
                if (rows[x][y].type == '#') {
                    galaxy.add(rows[x][y])
                }
            }
        }

        for (i in 0..<galaxy.size) {
            for (j in i + 1..<galaxy.size) {
                pairs.add(Pair(galaxy[i], galaxy[j]))
            }
        }

        return pairs
    }
}

data class Space(
    var x: Int,
    var y: Int,
    val type: Char,
    var f: Int = Int.MAX_VALUE,
    var g: Int = Int.MAX_VALUE,
    var parent: Space? = null
) : Comparable<Space> {
    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + y
        result = prime * result + x
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Space

        if (x != other.x) return false
        if (y != other.y) return false
        if (type != other.type) return false

        return true
    }

    override operator fun compareTo(other: Space): Int {
        if (f > other.f) return 1 else if (f < other.f) {
            return -1
        }
        return 0
    }

    override fun toString(): String {
        return "($x,$y)"
    }
}
