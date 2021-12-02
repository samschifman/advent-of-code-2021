import java.io.File

fun main(args: Array<String>) {
    println("Advent of Code Day 1 puzzle 1")

    var last = Integer.MAX_VALUE
    val count =
    File("./working/input_D01.txt").readLines().sumOf {
        val current = it.toInt()
        val result = if (current > last) 1 else 0
        last = current
        result
    }

    println("Count is $count")
}