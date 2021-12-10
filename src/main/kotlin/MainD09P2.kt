import java.io.File

private enum class Direction { CENTER, UP, DOWN, RIGHT, LEFT }

val checkedSet = mutableSetOf<Point>()

private fun countNeighbors(grid: List<List<Int>>, row: Int, col: Int, direction: Direction): Int =
    if (row < 0 || col < 0 || row >= grid.size || col >= grid[0].size || grid[row][col] == 9 || checkedSet.contains(
            Point(row, col)
        )
    ) {
        0
    } else {
        checkedSet.add(Point(row, col))
        1 + countNeighbors(grid, row - 1, col, Direction.UP) + countNeighbors(
            grid,
            row + 1,
            col,
            Direction.DOWN
        ) + countNeighbors(grid, row, col + 1, Direction.RIGHT) + countNeighbors(grid, row, col - 1, Direction.LEFT)
//        when (direction) {
//            Direction.CENTER -> {
//                1 + countNeighbors(grid, row - 1, col, Direction.UP) + countNeighbors(
//                    grid,
//                    row,
//                    col - 1,
//                    Direction.LEFT
//                ) + countNeighbors(grid, row + 1, col, Direction.DOWN) + countNeighbors(
//                    grid,
//                    row,
//                    col + 1,
//                    Direction.RIGHT
//                )
//            }
//            Direction.UP -> {
//                1 + countNeighbors(grid, row - 1, col, Direction.UP)
//            }
//            Direction.DOWN -> {
//                1 + countNeighbors(grid, row + 1, col, Direction.DOWN)
//            }
//            Direction.RIGHT -> {
//                1 + countNeighbors(grid, row, col + 1, Direction.RIGHT) + countNeighbors(grid, row - 1, col, Direction.UP) + countNeighbors(grid, row + 1, col, Direction.DOWN)
//            }
//            Direction.LEFT -> {
//                1 + countNeighbors(grid, row, col - 1, Direction.LEFT) + countNeighbors(grid, row - 1, col, Direction.UP) + countNeighbors(grid, row + 1, col, Direction.DOWN)
//            }
//            Direction.DIAG_UP_LEFT -> {
//                1 + countNeighbors(grid, row - 1, col, Direction.UP) + countNeighbors(
//                    grid,
//                    row,
//                    col - 1,
//                    Direction.LEFT
//                ) + countNeighbors(grid, row - 1, col - 1, Direction.DIAG_UP_LEFT)
//            }
//            Direction.DIAG_UP_RIGHT -> {
//                1 + countNeighbors(grid, row - 1, col, Direction.UP) + countNeighbors(
//                    grid,
//                    row,
//                    col + 1,
//                    Direction.RIGHT
//                ) + countNeighbors(grid, row - 1, col + 1, Direction.DIAG_UP_RIGHT)
//            }
//            Direction.DIAG_DOWN_LEFT -> {
//                1 + countNeighbors(grid, row + 1, col, Direction.DOWN) + countNeighbors(
//                    grid,
//                    row,
//                    col - 1,
//                    Direction.LEFT
//                ) + countNeighbors(grid, row + 1, col - 1, Direction.DIAG_DOWN_LEFT)
//            }
//            Direction.DIAG_DONW_RIGHT -> {
//                1 + countNeighbors(grid, row + 1, col, Direction.DOWN) + countNeighbors(
//                    grid,
//                    row,
//                    col + 1,
//                    Direction.RIGHT
//                ) + countNeighbors(grid, row + 1, col + 1, Direction.DIAG_DONW_RIGHT)
//            }
//        }
    }

fun main(args: Array<String>) {
    println("Advent of Code Day 9 puzzle 2")

    val grid = File("./working/input_D09.txt")
        .readLines()
        .map {
            it.trim().split("").filter { str -> str.isNotEmpty() }
        }
        .map {
            it.map { str -> str.toInt() }
        }


    val basinSizes = grid.flatMapIndexed { row, list ->
        list.mapIndexed { col, i ->
            if (((row > 0 && i < grid[row - 1][col]) || (row <= 0))
                && ((col > 0 && i < grid[row][col - 1]) || (col <= 0))
                && ((row < grid.size - 1 && i < grid[row + 1][col]) || (row >= grid.size - 1))
                && ((col < list.size - 1 && i < grid[row][col + 1]) || (col >= list.size - 1))
            ) {
                val test = countNeighbors(grid, row, col, Direction.CENTER)
                println("Value for ${row},${col} is ${test}")
                test
            } else {
                0
            }
        }
    }
        .sorted()
        .reversed()
        .take(3)
        .reduce { acc, i -> acc * i }
//        .forEachIndexed {  index, igfgfb ->
//            println("Selected Basin Size for ${index} is ${igfgfb}")
//        }
//        .fold(1) { acc, i -> acc * i }

    println("Basin Size total is ${basinSizes}")
}
