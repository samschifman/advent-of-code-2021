import java.io.File
import kotlin.math.abs

fun main(args: Array<String>) {
    println("Advent of Code Day 7 puzzle 2")

    val positions = File("./working/input_D07_test.txt")
        .readLines()
        .flatMap { it.split(",") }
        .map { it.toInt() }

    val mean = positions.average().toInt()

    val fuel = (mean-2..mean+2)
        .map { meanTry -> positions.map { abs(meanTry - it).partialSum() }.sum() }
        .minOf { it }

    println("Fuel used is ${fuel}")
}
