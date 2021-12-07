import java.io.File
import kotlin.math.abs


fun main(args: Array<String>) {
    println("Advent of Code Day 7 puzzle 1")

    val positions = File("./working/input_D07.txt")
        .readLines()
        .flatMap { it.split(",") }
        .map { it.toInt() }

    val median = positions.median()

    val fuel = positions.map { abs(median - it) }.sum();

    println("Fuel used is ${fuel}")
}
