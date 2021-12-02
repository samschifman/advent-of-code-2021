import java.io.File

enum class Command{
    UP, DOWN, FORWARD
}

data class CourseChange(val command: Command, val magnitude: Int)

fun main(args: Array<String>) {
    println("Advent of Code Day 2 puzzle 1")

    var depth = 0;
    var position = 0;
    val count =
        File("./working/input_D02.txt")
            .readLines()
            .map {
                it.split(" ")
            }
            .map {
                CourseChange(command = Command.valueOf(it[0].uppercase()), magnitude = it[1].toInt())
            }
            .forEach {
                when (it.command) {
                    Command.UP -> depth -= it.magnitude
                    Command.DOWN -> depth += it.magnitude
                    Command.FORWARD -> position += it.magnitude
                }
            }

    println("Postion is $position, Depth is $depth, Answer is ${depth * position}")
}