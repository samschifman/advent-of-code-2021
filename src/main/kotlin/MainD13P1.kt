import java.io.File



fun main(args: Array<String>) {
    println("Advent of Code Day 13 puzzle 1")

    val pathname = "./working/input_D13.txt"

    val points = File(pathname)
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

    val remaining = foulds[0].doFold(points).size

    println("Number of visible dots is ${remaining}")
}
