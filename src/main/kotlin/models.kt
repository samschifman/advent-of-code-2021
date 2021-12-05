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

















