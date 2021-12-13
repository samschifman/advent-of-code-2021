import java.io.File


private fun incrementDay(grid: List<MutableList<Int>>) {
    for (x in 0..9) {
        for (y in 0..9) {
            grid[x][y]++
        }
    }
}

private fun incIfInRange(grid: List<MutableList<Int>>, x: Int, y: Int) {
    if (x in 0..9 && y in 0..9 && grid[x][y] > 0) {
        grid[x][y]++
    }
}

private fun incSurrounding(grid: List<MutableList<Int>>, x: Int, y: Int) {
    incIfInRange(grid, x - 1, y - 1)
    incIfInRange(grid, x - 1, y)
    incIfInRange(grid, x - 1, y + 1)
    incIfInRange(grid, x + 1, y - 1)
    incIfInRange(grid, x + 1, y)
    incIfInRange(grid, x + 1, y + 1)
    incIfInRange(grid, x, y - 1)
    incIfInRange(grid, x, y + 1)
}

private fun cascade(grid: List<MutableList<Int>>): Long {
    var numberFlash = 0L;

    for (x in 0..9) {
        for (y in 0..9) {
            if (grid[x][y] > 9) {
                grid[x][y] = 0
                incSurrounding(grid, x, y)
                numberFlash++
            }
        }
    }

    return if (numberFlash > 0) {
        numberFlash + cascade(grid)
    } else {
        0L
    }
}

private fun printGrid(step: Int, grid: List<MutableList<Int>>) {
    println("Grid for step $step")
    grid.forEach { println(it) }
    println()
}

fun main(args: Array<String>) {
    println("Advent of Code Day 11 puzzle 1")

    var grid = File("./working/input_D11.txt")
        .readLines()
        .map {
            it.trim()
                .split("")
                .filter { str -> str.isNotBlank() }
                .map { str -> str.toInt() }
                .toMutableList()
        }


    printGrid(-1, grid)

    val flashes = (0 until 100)
        .map {
            incrementDay(grid)
            val number = cascade(grid)
            printGrid(it, grid)
            number
        }
        .sum()


    println("Flashes is $flashes")
}
