import java.io.File
import kotlin.math.abs

fun main(args: Array<String>) {
    println("Advent of Code Day 8 puzzle 1")

    val numberOfSimpleSegments = File("./working/input_D08.txt")
        .readLines()
        .map {
            it.split("|") }
        .flatMap {
            it[1].trim().split(" ")
        }
        .map { it.length }
        .filter {
            it == 2 || it == 4 || it == 3 || it == 7
        }
        .count()

    println("Number of simple segments is ${numberOfSimpleSegments}")
}
