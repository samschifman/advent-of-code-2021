import java.io.File


fun plotPoints(points: Set<Point>) {
    val maxX = points.map { it.x }.maxOf { it } + 1
    val maxY = points.map { it.y }.maxOf { it } + 1

    for (y in 0..maxY) {
        for (x in 0..maxX) {
            if (points.contains(Point(x,y))) {
                print("#")
            } else {
                print("-")
            }
        }
        println()
    }
}

fun main(args: Array<String>) {
    println("Advent of Code Day 13 puzzle 2")

    val pathname = "./working/input_D13.txt"

    var points = File(pathname)
        .readLines()
        .filter {
            it.isNotBlank() && it[0].isDigit()
        }
        .map {
            it.trim().split(",")
        }
        .map {
            Point(it[0].toInt(), it[1].toInt())
        }
        .toSet()


    val foulds = File(pathname)
        .readLines()
        .filter {
            it.isNotBlank() && it.startsWith("fold")
        }
        .map {
            Fold.fromInstruction(it)
        }

    foulds.forEach { points = it.doFold(points) }

    val remaining = points.size

    println("Number of visible dots is ${remaining}")

    plotPoints(points)
}
