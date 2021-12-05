import java.io.File


fun main(args: Array<String>) {
    println("Advent of Code Day 5 puzzle 2")
    val GRID_SIZE = 1000

    val lines = File("./working/input_D05.txt").readLines()
        .map { Line.toLine(it) }

    var overlapCount = 0;
    (0..GRID_SIZE).forEach { x ->
        (0..GRID_SIZE).forEach { y ->
            val point = Point(x, y)

            if (lines.filter { it.contains(point) }.size > 1) {
                overlapCount++
            }
        }
    }

    println("Overlap is ${overlapCount}")
}
