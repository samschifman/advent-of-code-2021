import java.io.File
import kotlin.math.abs

fun main(args: Array<String>) {
    println("Advent of Code Day 9 puzzle 1")

    val grid = File("./working/input_D09.txt")
        .readLines()
        .map {
            it.trim().split("").filter { str -> str.isNotEmpty() }
        }
        .map {
            it.map { str -> str.toInt() }
        }


    val riskLevelTotal = grid.flatMapIndexed { row, list ->
        list.filterIndexed { col, i ->
            ((row > 0 && i < grid[row-1][col]) || (row <=0))
                    && ((col > 0 && i < grid[row][col-1]) || (col <=0))
                    && ((row < grid.size-1 && i < grid[row+1][col]) || (row >= grid.size-1))
                    && ((col < list.size-1 && i < grid[row][col+1]) || (col >= list.size-1))
        }.map { it+1 }
    }.sum()

    println("Risk level total is ${riskLevelTotal}")
}
