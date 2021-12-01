import java.io.File

fun main(args: Array<String>) {
    println("Advent of Code Day 1 puzzle 2")

    var last = Integer.MAX_VALUE
    val count =
        File("./working/input_D01.txt")
            .readLines()
            .map {
                it.toInt()
            }
            .windowed(3)
            .count {
                val current = it.sum()
                val greater = current > last
                last = current
                greater
            }

    println("Count is $count")
}