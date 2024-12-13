package twentythree

import println
import readInput
import java.util.*
import kotlin.math.abs

fun main() {
    val input = readInput("Day10")
    val map = Map(input)

    val pathFinder = Path(map, map.start, map.start)

    pathFinder.astar()

    return
}

class Path(val map: Map, private val start: Pipe, private val end: Pipe) {

    /**
     * A star will actually fail to find the shortest path because there is only one path, but A star
     * will exactly stop at the mid-point because at that stage, we would have visited all possible nodes
     * from both directions. So that's our midpoint. The G cost of that node is the answer
     */
    fun astar() {
        if (map.at(start) == '.') return

        val open = PriorityQueue<Pipe>()
        val closed = mutableSetOf<Pipe>()
        start.g = 0
        start.f = manhattan(start)
        open.add(start)

        var current = start

        while (open.isNotEmpty()) {
            current = open.poll()
            closed.add(current)

            if (current == end && current.g != 0) return

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

        // Potentially found midpoint of loop
        current.g.println()
        return
    }

    private fun manhattan(p: Pipe): Int {
        val dx: Int = abs(p.x - end.x)
        val dy: Int = abs(p.y - end.y)

        return dx + dy
    }
}

class Map(input: List<String>) {
    private val rows = mutableListOf<MutableList<Pipe>>()

    lateinit var start: Pipe

    init {
        for ((x, line) in input.withIndex()) {
            val column = mutableListOf<Pipe>()

            for ((y, type) in line.withIndex()) {
                column.add(Pipe(x, y, type))

                if (type == 'S') {
                    start = column[y]
                }
            }

            rows.add(column)
        }
    }

    fun at(pipe: Pipe): Char {
        return rows[pipe.x][pipe.y].type
    }

    fun neighbours(pipe: Pipe): List<Pipe> {
        val neighbours = mutableListOf<Pipe>()
        val currentPipe = rows[pipe.x][pipe.y].type

        if (pipe.y > 0) {
            val type = rows[pipe.x][pipe.y - 1].type
            if ((currentPipe == 'S' || currentPipe == '-' || currentPipe == '7' || currentPipe == 'J') && (type == '-' || type == 'F' || type == 'L')) {
                neighbours.add(rows[pipe.x][pipe.y - 1])
            }
        }

        if (pipe.y < rows[0].size - 1) {
            val type = rows[pipe.x][pipe.y + 1].type
            if ((currentPipe == 'S' || currentPipe == '-' || currentPipe == 'F' || currentPipe == 'L') && (type == '-' || type == 'J' || type == '7')) {
                neighbours.add(rows[pipe.x][pipe.y + 1])
            }
        }

        if (pipe.x > 0) {
            val type = rows[pipe.x - 1][pipe.y].type
            if ((currentPipe == 'S' || currentPipe == '|' || currentPipe == 'J' || currentPipe == 'L') && (type == '|' || type == 'F' || type == '7')) {
                neighbours.add(rows[pipe.x - 1][pipe.y])
            }
        }

        if (pipe.x < rows.size - 1) {
            val type = rows[pipe.x + 1][pipe.y].type

            if ((currentPipe == 'S' || currentPipe == '|' || currentPipe == 'F' || currentPipe == '7') && (type == '|' || type == 'L' || type == 'J')) {
                neighbours.add(rows[pipe.x + 1][pipe.y])
            }
        }

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
