import java.io.File


fun main(args: Array<String>) {
    println("Advent of Code Day 17 puzzle 2")

    val pathname = "./working/input_D17.txt"

    val targetPoints = File(pathname)
        .readLines()
        .first()
        .split(",")
        .let { x_y ->
            // Premodified Input to remove extra stuff
            val xs = x_y[0].split("..").filter { it.isNotBlank() }.map { it.toInt() }
            val ys = x_y[1].split("..").filter { it.isNotBlank() }.map { it.toInt() }
            (xs[0]..xs[1]).flatMap { x ->
                (ys[0]..ys[1]).map { y ->
                    Point(x, y)
                }
            }
        }

    val count = (0..2000).flatMap { vx ->
        (-1000..2000).map { vy -> Probe(vx, vy).fire(targetPoints) }
    }
        .filter { it.isNotEmpty() }
        .count()

    println("Count is: $count")
}
