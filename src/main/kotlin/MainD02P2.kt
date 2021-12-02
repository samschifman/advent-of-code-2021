import java.io.File

fun main(args: Array<String>) {
    println("Advent of Code Day 2 puzzle 2")

    var depth = 0;
    var position = 0;
    var aim = 0;
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
                    Command.UP -> aim -= it.magnitude
                    Command.DOWN -> aim += it.magnitude
                    Command.FORWARD -> {
                        position += it.magnitude
                        depth += aim * it.magnitude
                    }
                }
            }

    println("Postion is $position, Depth is $depth, Answer is ${depth * position}")
}