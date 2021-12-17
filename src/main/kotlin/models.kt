import java.math.BigInteger
import java.util.*


enum class Direction { CENTER, UP, DOWN, RIGHT, LEFT }

enum class Command {
    UP, DOWN, FORWARD
}

data class CourseChange(val command: Command, val magnitude: Int)


data class DiagnosticCounter(
    val countZero: List<Int> = listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
    val countOnes: List<Int> = listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
)

class BingoSquare(val number: Int, var picked: Boolean)

class BingoRow(numbers: List<Int>) {
    private val squares: List<BingoSquare>

    init {
        squares = numbers.map { BingoSquare(it, false) }
    }

    fun mark(draw: Int) = squares.find { it.number == draw }?.let {
        it.picked = true
    }

    fun isMarked(column: Int) = squares[column].picked

    fun isAllMarked() = squares.all { it.picked }

    fun unmarked() = squares.filter { !it.picked }
}

class BingBoard(lines: List<String>, var columnCount: Int = 5) {
    private val rows: List<BingoRow>

    init {
        rows = lines.map { BingoRow(it.split(" ").filter { it.isNotBlank() }.map { it.toInt() }) }
    }

    fun mark(draw: Int) = rows.forEach { it.mark(draw) }

    fun isWinner() = isRowWinner() || isColumnWinner()

    fun score() = rows.flatMap { it.unmarked() }.map { it.number }.sum()

    private fun isRowWinner() = rows.any { it.isAllMarked() }

    private fun isColumnWinner() = (0 until columnCount).map { isColumnWinner(it) }.contains(true)

    private fun isColumnWinner(column: Int) = rows.map { it.isMarked(column) }.all { it }
}

data class BingoWinner(val draw: Int, val boardScore: Int)

data class Point(val x: Int, val y: Int) {
    companion object {
        fun toPoint(text: String): Point {
            val coords = text.split(",")
            return Point(coords[0].toInt(), coords[1].toInt())
        }
    }

    fun isAfter(point: Point): Boolean = (point.x < x || point.y < y)
    fun isAfterOrSame(point: Point): Boolean = (point.x <= x || point.y <= y)
    fun isBefore(point: Point): Boolean = (point.x > x || point.y > y)
    fun isBeforeOrSame(point: Point): Boolean = (point.x >= x || point.y >= y)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }


}

data class Line(val start: Point, val end: Point) {
    val pointsOnLine: List<Point>

    init {
        if (isSlanted()) {
            val points = mutableListOf<Point>()
            var currentPoint = start
            while (currentPoint != end) {
                points.add(currentPoint)
                currentPoint = currentPoint.copy(
                    x = if (start.x < end.x) currentPoint.x + 1 else if (start.x > end.x) currentPoint.x - 1 else currentPoint.x,
                    y = if (start.y < end.y) currentPoint.y + 1 else if (start.y > end.y) currentPoint.y - 1 else currentPoint.y
                )
            }
            points.add(end)
            pointsOnLine = points.toList()
        } else {
            pointsOnLine = emptyList()
        }
    }

    companion object {
        fun toLine(text: String): Line {
            val points = text.split(Regex(" -> ")).map { Point.toPoint(it) }
            return if (points[0].x <= points[1].x && points[0].y <= points[1].y) Line(points[0], points[1])
            else if (points[0].x > points[1].x && points[0].y > points[1].y) Line(points[1], points[0])
            else if (points[0].x == points[1].x && points[0].y < points[1].y) Line(points[0], points[1])
            else if (points[0].x == points[1].x && points[0].y > points[1].y) Line(points[1], points[0])
            else if (points[0].y == points[1].y && points[0].x < points[1].x) Line(points[0], points[1])
            else if (points[0].y == points[1].y && points[0].x > points[1].x) Line(points[1], points[0])
            else Line(points[0], points[1])
        }
    }

    fun isVertical() = start.x == end.x
    fun isHorizontal() = start.y == end.y
    fun isSlanted() = !isHorizontal() && !isVertical()

    fun contains(point: Point): Boolean =
        if (isSlanted()) inSlant(point)
        else if (start.x != point.x && start.y != point.y) false
        else if (start.x == point.x && start.y == point.y) true
        else if (end.x == point.x && end.y == point.y) true
        else if (start.x == point.x) (start.y..end.y).contains(point.y)
        else if (start.y == point.y) (start.x..end.x).contains(point.x)
        else false

    private fun inSlant(point: Point): Boolean = pointsOnLine.contains(point)

}


class Lanternfish(var age: Int = 8) {

    fun addDay(): Boolean {
        age -= 1

        return if (age < 0) {
            age = 6
            true
        } else {
            false
        }
    }
}


typealias Path = List<Cave>

fun Path.mostSmallVisits(): Int =
    this.filter {
        !it.large
    }.map {
        this.count { cave -> cave == it }
    }.maxOf {
        it
    }


data class Trip(val allowedVisits: Int = 1, val path: Path /* = kotlin.collections.List<Cave> */ = emptyList()) {
    fun overVisit(cave: Cave): Boolean = !cave.large
            && ((cave.name == "start" && path.contains(cave)) || (path.contains(cave) && path.mostSmallVisits() >= allowedVisits))

    fun visit(cave: Cave): Trip = this.copy(path = path.plus(cave))
}

class Cave(val name: String) {
    val large = name == name.uppercase()
    val tunnels = mutableListOf<Cave>()

    fun enter(trip: Trip = Trip()): List<Trip> {
        return if (trip.overVisit(this)) {
            emptyList()
        } else if (name == "end") {
            listOf(trip.visit(this))
        } else {
            tunnels.map {
                it.enter(trip.visit(this))
            }.flatten()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cave
        if (name != other.name) return false
        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        return "Cave(name='$name', large=$large, tunnels=(${tunnels.map { it.name }}))"
    }
}

data class Fold(val direction: Direction, val line: Int) {

    fun doFold(points: Set<Point>): Set<Point> = when (direction) {
        Direction.LEFT -> points
            .map {
                if (it.x < line)
                    it
                else
                    it.copy(x = line - (it.x - line))
            }.toSet()
        Direction.UP -> points
            .map {
                if (it.y < line)
                    it
                else
                    it.copy(y = line - (it.y - line))
            }.toSet()
        else -> throw Exception("I don't know how to fold that way!")
    }

    companion object {
        fun fromInstruction(text: String): Fold {
            val parts = text.split("=")
            return Fold(
                direction = if (parts[0].endsWith("x")) Direction.LEFT else Direction.UP,
                line = parts[1].toInt()
            )
        }
    }
}

data class Graph<Point>(
    val vertices: Set<Point>,
    val edges: Map<Point, Set<Point>>,
    val weights: Map<Pair<Point, Point>, Int>
) {
    constructor(weights: Map<Pair<Point, Point>, Int>): this(
        vertices = weights.keys.toList().getUniqueValuesFromPairs(),
        edges = weights.keys
            .groupBy { it.first }
            .mapValues { it.value.getUniqueValuesFromPairs { x -> x !== it.key } }
            .withDefault { emptySet() },
        weights = weights
    )
}

fun <Point> dijkstra(graph: Graph<Point>, start: Point): Map<Point, Point?> {
    val S: MutableSet<Point> = mutableSetOf() // a subset of vertices, for which we know the true distance

    /*
     * delta represents the length of the shortest distance paths
     * from start to v, for v in vertices.
     *
     * The values are initialized to infinity, as we'll be getting the key with the min value
     */
    val delta = graph.vertices.map { it to Int.MAX_VALUE }.toMap().toMutableMap()
    delta[start] = 0

    val previous: MutableMap<Point, Point?> = graph.vertices.map { it to null }.toMap().toMutableMap()

    while (S != graph.vertices) {
        // let v be the closest vertex that has not yet been visited
        val v: Point = delta
            .filter { !S.contains(it.key) }
            .minByOrNull {
                it.value
            }!!
            .key

        graph.edges.getValue(v).minus(S).forEach { neighbor ->
            val newPath = delta.getValue(v) + graph.weights.getValue(Pair(v, neighbor))

            if (newPath < delta.getValue(neighbor)) {
                delta[neighbor] = newPath
                previous[neighbor] = v
            }
        }

        S.add(v)
    }

    return previous.toMap()
}

fun <Point> shortestPath(shortestPathTree: Map<Point, Point?>, start: Point, end: Point): List<Point> {
    fun pathTo(start: Point, end: Point): List<Point> {
        if (shortestPathTree[end] == null) return listOf(end)
        return listOf(pathTo(start, shortestPathTree[end]!!), listOf(end)).flatten()
    }

    return pathTo(start, end)
}


fun addToMap(grid: List<List<Int>>, point: Point, graphMap: MutableMap<Pair<Point, Point>, Int>, direction: Direction = Direction.CENTER) {
    fun addNeighbor(x: Int, y: Int, direction: Direction) {
        val otherPoint = Point(x, y)
        if (!graphMap.containsKey(point to otherPoint)) {
            graphMap[point to otherPoint] = grid[y][x]
            if (direction == Direction.CENTER) {
                addToMap(grid, otherPoint, graphMap, direction)
            }
        }
    }
    if (point.x - 1 >= 0 && direction != Direction.LEFT) {
        addNeighbor(point.x - 1, point.y, Direction.LEFT)
    }
    if (point.y - 1 >= 0 && direction != Direction.DOWN) {
        addNeighbor(point.x, point.y - 1, Direction.DOWN)
    }
    if (point.x + 1 < grid[0].size) {
        addNeighbor(point.x + 1, point.y, direction)
    }
    if (point.y + 1 < grid.size) {
        addNeighbor(point.x, point.y + 1, direction)
    }
}


abstract class Packet(val version: Int, val type: Int) {

    open fun totalVersions(): Int = this.version
    abstract fun value(): Long
    abstract fun printEquation(): String

    companion object {
        fun parsePacket(byteArray: List<String>): Pair<Int, Packet> {
            var take = 0
            var remainingBytes = byteArray
            val version = BigInteger(remainingBytes.take(3).joinToString(""), 2).toInt()
            remainingBytes = remainingBytes.drop(3)
            take += 3
            val type = BigInteger(remainingBytes.take(3).joinToString(""), 2).toInt()
            remainingBytes = remainingBytes.drop(3)
            take += 3

            return if (type == 4) {
                var done = false
                var contents = mutableListOf<String>()
                while (!done) {
                    done = remainingBytes[0] == "0"
                    remainingBytes = remainingBytes.drop(1)
                    take++
                    val digit = remainingBytes.take(4)
                    remainingBytes = remainingBytes.drop(4)
                    take += 4
                    contents.add(digit.joinToString(""))
                }
                take to LiteralPacket(version, BigInteger(contents.joinToString(""), 2).toLong())
            } else {
                val lengthIndicator = remainingBytes.take(1).first()
                remainingBytes = remainingBytes.drop(1)
                take += 1
                val length = if (lengthIndicator.toInt() == 0) 15 else 11

                val contents = mutableListOf<Packet>()
                if (lengthIndicator.toInt() == 0) {
                    val numberPacketBytes = BigInteger(remainingBytes.take(15).joinToString(""), 2).toInt()
                    remainingBytes = remainingBytes.drop(15)
                    take += 15
                    var subPacketBytes = remainingBytes.take(numberPacketBytes)
                    take += numberPacketBytes

                    while (subPacketBytes.isNotEmpty() && subPacketBytes.contains("1")) {
                        val nextPacket = parsePacket(subPacketBytes)
                        subPacketBytes = subPacketBytes.drop(nextPacket.first)
                        contents.add(nextPacket.second)
                    }
                } else {
                    val numberOfPacket = BigInteger(remainingBytes.take(11).joinToString(""), 2).toInt()
                    remainingBytes = remainingBytes.drop(11)
                    take += 11

                    var i = 0
                    while (i++ < numberOfPacket && remainingBytes.isNotEmpty() && remainingBytes.contains("1")) {
                        val nextPacket = parsePacket(remainingBytes)
                        remainingBytes = remainingBytes.drop(nextPacket.first)
                        take += nextPacket.first
                        contents.add(nextPacket.second)
                    }

                    if (numberOfPacket != contents.size) {
                        println("WARING: expected $numberOfPacket and got ${contents.size} sub-packets")
                    }
                }
                take to OpperatorPacket(version, type, contents)
            }
        }
    }
}

class LiteralPacket(version: Int, val contents: Long) : Packet(version, 4) {
    override fun value(): Long = contents
    override fun printEquation(): String = contents.toString()
}

class OpperatorPacket(version: Int, type: Int, val contents: List<Packet>) : Packet(version, type) {

    override fun totalVersions(): Int = this.contents.sumOf { it.totalVersions() } + this.version

    override fun value(): Long = when (type) {
        0 -> contents.sumOf { it.value() }
        1 -> contents.map { it.value() }.reduce { acc, l -> acc * l }
        2 -> contents.minOf { it.value() }
        3 -> contents.maxOf { it.value() }
        5 -> if (contents[0].value() > contents[1].value()) 1 else 0
        6 -> if (contents[0].value() < contents[1].value()) 1 else 0
        7 -> if (contents[0].value() == contents[1].value()) 1 else 0
        else -> throw Exception("Unknown function")
    }

    override fun printEquation(): String = " (" + when (type) {
        0 -> contents.joinToString(" + ") { it.printEquation() }
        1 -> contents.joinToString(" * ") { it.printEquation() }
        2 -> "minOF[" + contents.joinToString(", ") { it.printEquation() } + "]"
        3 -> "maxOF[" + contents.joinToString(", ") { it.printEquation() } + "]"
        5 -> contents[0].printEquation() + " > " + contents[1].printEquation()
        6 -> contents[0].printEquation() + " < " + contents[1].printEquation()
        7 -> contents[0].printEquation() + " = " + contents[1].printEquation()
        else -> throw Exception("Unknown function")
    } + ") "
}

class Probe(var vx: Int, var vy: Int) {

    private fun move(position: Point): Point {
        val next = position.copy(x = position.x + vx, y = position.y + vy)

        vx = if (vx == 0) 0 else if (vx < 0) vx + 1 else vx - 1
        vy -= 1

        return next;
    }

    private fun miss(position: Point, lowerRight: Point): Boolean =
        position.x > lowerRight.x || position.y < lowerRight.y

    fun fire(target: List<Point>): List<Point> {
        var position = Point(0, 0)
        val tragetory = mutableListOf(position)

        val lowerRight = Point(target.map { it.x }.maxOf { it }, target.map { it.y }.minOf { it })

        while (!target.contains(position)) {
            position = move(position)
            tragetory.add(position)
            if (miss(position, lowerRight)) {
                return emptyList()
            }
        }

        return tragetory
    }
}
