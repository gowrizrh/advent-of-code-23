import java.util.*
import kotlin.math.abs

fun main() {
    val input = readInput("Day10")
    val map = Map(input)
    val start = Pipe(1, 1, '.')
    val end = Pipe(3, 3, 'J')
    val pathFinder = Path(map, start, end)

    pathFinder.findPath()


    return
}

class Path(val map: Map, val start: Pipe, val end: Pipe) {


    // TODO: Change name
    fun findPath() {
        if (map.at(start) == '.') return

        val open = PriorityQueue<Pipe>()
        val closed = mutableSetOf<Pipe>()
        start.g = 0
        start.f = manhattan(start)
        open.add(start)

        var current: Pipe

        while (open.isNotEmpty()) {
            current = open.poll()
            closed.add(current)

            if (current == end) println("Found path")

            for (neighbour in map.neighbours(current)) {
                if (map.at(neighbour) == '.' || closed.contains(neighbour)) continue;
                val tentativeG: Int = current.g + 1

                if (tentativeG < neighbour.g || !open.contains(neighbour)) {
                    val f = tentativeG + manhattan(neighbour)
                    neighbour.g = tentativeG
                    neighbour.f = f
                    neighbour.parent = current

                    if (!open.contains(neighbour)) open.add(neighbour)
                }
            }
        }
    }

    fun manhattan(p: Pipe): Int {
        val dx: Int = abs(p.x - end.x)
        val dy: Int = abs(p.y - end.y)

        return dx + dy
    }
}

class Map(input: List<String>) {
    val rows = mutableListOf<MutableList<Pipe>>()

    init {
        for ((x, line) in input.withIndex()) {
            val column = mutableListOf<Pipe>()

            for ((y, type) in line.withIndex()) {
                column.add(Pipe(x, y, type))
            }

            rows.add(column)
        }
    }

    fun at(x: Int, y: Int): Char {
        return rows[x][y].type
    }

    fun at(pipe: Pipe): Char {
        return rows[pipe.x][pipe.y].type
    }

    fun neighbours(pipe: Pipe): List<Pipe> {
        val neighbours = mutableListOf<Pipe>()

        if (pipe.y > 0) neighbours.add(rows[pipe.x][pipe.y - 1])
        if (pipe.y < rows[0].size - 1) neighbours.add(rows[pipe.x][pipe.y + 1])
        if (pipe.x > 0) neighbours.add(rows[pipe.x - 1][pipe.y])
        if (pipe.x < rows.size - 1) neighbours.add(rows[pipe.x + 1][pipe.y])

        return neighbours
    }
}

data class Pipe(
    val x: Int,
    val y: Int,
    val type: Char,
    var f: Int = Int.MAX_VALUE,
    var g: Int = Int.MAX_VALUE,
    var parent: Pipe? = null
) : Comparable<Pipe> {
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

        other as Pipe

        if (x != other.x) return false
        if (y != other.y) return false
        if (type != other.type) return false

        return true
    }

    override operator fun compareTo(other: Pipe): Int {
        if (f > other.f) return 1 else if (f < other.f) {
            return -1
        }
        return 0
    }

    override fun toString(): String {
        return "($x,$y)"
    }
}
