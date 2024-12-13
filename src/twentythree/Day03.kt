package twentythree

import readInput

val symbols = Regex("[^0-9.]")
val digits = Regex("[0-9]")

data class Location(val x: Int, val y: Int, val value: Char) {
    override fun toString(): String {
        return "$value"
    }
}

data class LocationNumber(val x: Int, val y: Int, val value: Int) {
    override fun hashCode(): Int {
        var hash = 7
        hash = 31 * hash + x
        hash = 31 * hash + y
        hash = 31 * hash + value

        return hash
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LocationNumber

        if (x != other.x) return false
        if (y != other.y) return false
        if (value != other.value) return false

        return true
    }
}

class Grid(input: List<String>) {
    val rows = mutableListOf<MutableList<Location>>()

    init {
        for ((x, line) in input.withIndex()) {
            val column = mutableListOf<Location>()

            for ((y, token) in line.withIndex()) {
                column.add(Location(x, y, token))
            }

            rows.add(column)
        }
    }

    fun hasSymbol(x: Int, y: Int): Boolean {
        val neighbours = mutableListOf<Location>()

        if (x > 0) {
            // top left
            if (y > 0)
                neighbours.add(rows[x - 1][y - 1])

            neighbours.add(rows[x - 1][y])

            // top right
            if (y < rows.size - 1)
                neighbours.add(rows[x - 1][y + 1])

        }

        if (x < rows.size - 1) {
            // bottom left
            if (y > 0)
                neighbours.add(rows[x + 1][y - 1])

            neighbours.add(rows[x + 1][y])

            // bottom right
            if (y < rows[0].size - 1)
                neighbours.add(rows[x + 1][y + 1])
        }

        if (y > 0) {
            neighbours.add(rows[x][y - 1])
        }

        if (y < rows[0].size - 1) {
            neighbours.add(rows[x][y + 1])
        }

        return neighbours.any {
            symbols.matches(it.toString())
        }
    }

    fun isGear(x: Int, y: Int): Boolean {
        val neighbours = mutableListOf<Location>()

        if (x > 0) {
            // top left
            if (y > 0)
                neighbours.add(rows[x - 1][y - 1])

            neighbours.add(rows[x - 1][y])

            // top right
            if (y < rows.size - 1)
                neighbours.add(rows[x - 1][y + 1])

        }

        if (x < rows.size - 1) {
            // bottom left
            if (y > 0)
                neighbours.add(rows[x + 1][y - 1])

            neighbours.add(rows[x + 1][y])

            // bottom right
            if (y < rows[0].size - 1)
                neighbours.add(rows[x + 1][y + 1])
        }

        if (y > 0) {
            neighbours.add(rows[x][y - 1])
        }

        if (y < rows[0].size - 1) {
            neighbours.add(rows[x][y + 1])
        }

        return neighbours.filter {
            digits.matches(it.toString())
        }.size > 1
    }

    fun getNeighbours(x: Int, y: Int): List<Location> {
        val neighbours = mutableListOf<Location>()

        if (x > 0) {
            // top left
            if (y > 0)
                neighbours.add(rows[x - 1][y - 1])

            neighbours.add(rows[x - 1][y])

            // top right
            if (y < rows.size - 1)
                neighbours.add(rows[x - 1][y + 1])

        }

        if (x < rows.size - 1) {
            // bottom left
            if (y > 0)
                neighbours.add(rows[x + 1][y - 1])

            neighbours.add(rows[x + 1][y])

            // bottom right
            if (y < rows[0].size - 1)
                neighbours.add(rows[x + 1][y + 1])
        }

        if (y > 0) {
            neighbours.add(rows[x][y - 1])
        }

        if (y < rows[0].size - 1) {
            neighbours.add(rows[x][y + 1])
        }

        return neighbours
    }

    fun getNumber(x: Int, y: Int): LocationNumber {
        var startY = y

        // go back until we find a non digit character
        while (startY - 1 >= 0 && rows[x][startY - 1].value.isDigit()) {
            startY--
        }

        var numbers = ""
        for (index in startY..<rows[0].size) {
            val column = rows[x][index]

            if (column.value.isDigit()) {
                numbers = "$numbers${rows[x][index].value}"
            }

            if (!column.value.isDigit() || index + 1 == rows.size) {
                break;
            }
        }

        return LocationNumber(x, startY, numbers.toInt())
    }
}

fun main() {
    val input = readInput("Day03")
    val schematic = Grid(input)
    val engineParts = mutableListOf<Int>()

    // Part 1
//    for ((x, row) in schematic.rows.withIndex()) {
//        var numbers = ""
//        var isEnginePart = false
//
//        for ((y, column) in row.withIndex()) {
//            if (column.value.isDigit()) {
//                if (!isEnginePart)
//                    isEnginePart = schematic.hasSymbol(x, y)
//
//                numbers = "$numbers${column.value}"
//            }
//
//            if (!column.value.isDigit() || y + 1 == schematic.rows.size) {
//                if (isEnginePart)
//                    engineParts.add(numbers.toInt())
//
//                numbers = ""
//                isEnginePart = false
//            }
//        }
//    }

//    engineParts.sum().println()

    // Part 2
    val gearNumbers = mutableListOf<Int>()

    for ((x, row) in schematic.rows.withIndex()) {
        for ((y, column) in row.withIndex()) {
            if (column.value == '*') {
                val neighbours = schematic.getNeighbours(x, y)
                    .filter { digits.matches(it.toString()) }

                val set = mutableSetOf<LocationNumber>()

                if (neighbours.size > 1) {
                    for (neighbour in neighbours) {
                        val number = schematic.getNumber(neighbour.x, neighbour.y)
                        set.add(number)
                    }

                    if (set.size != 2) {
                        println("Something has gone sideways")
                    }

//                    gearNumbers.add(
//                        set.reduce { acc, it ->
//                            acc * it
//                        }
//                    )
                }
            }
        }
    }

//    gearNumbers.sum().println()
}