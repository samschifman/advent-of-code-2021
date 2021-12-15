import java.io.File

var minRisk = Int.MAX_VALUE

data class Route(val path: List<Cell> = emptyList(), val riskTotal: Int = 0)

class Cell(val cords: Point, val riskLevel: Int) {
    var connected = mutableListOf<Cell>()

    fun findNeighbors(grid: List<List<Int>>, cells: MutableList<Cell>) {
//        if (cords.x - 1 > 0) {
//            addNeighbor(grid, cells, cords.x - 1, cords.y)
//        }
//        if (cords.y - 1 > 0) {
//            addNeighbor(grid, cells, cords.x, cords.y - 1)
//        }
        if (cords.x + 1 < grid[0].size) {
            addNeighbor(grid, cells, cords.x + 1, cords.y)
        }
        if (cords.y + 1 < grid.size) {
            addNeighbor(grid, cells, cords.x, cords.y + 1)
        }
    }

    private fun addNeighbor(grid: List<List<Int>>, cells: MutableList<Cell>, x: Int, y: Int) {
        val neighbor = Cell(Point(x, y), grid[y][x])
        if (cells.contains(neighbor)) {
            connected.add(cells.find { it == neighbor }!!)
        } else {
            cells.add(neighbor)
            neighbor.findNeighbors(grid, cells)
            connected.add(neighbor)
        }

    }

    fun enter(route: Route = Route()): List<Route> =
        if (route.riskTotal + riskLevel > minRisk) {
            println("- $minRisk")
            emptyList()
        } else if (this.connected.isEmpty()) {
            println("x")
            minRisk = route.riskTotal + riskLevel
            listOf(route.copy(path = route.path + this, riskTotal = route.riskTotal + riskLevel))
        } else {
            print(".")
            connected
                .filter { !route.path.contains(it) }
                .map {
                    it.enter(route.copy(path = route.path + this, riskTotal = route.riskTotal + riskLevel))
                }
                .flatten()
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cell

        if (cords != other.cords) return false

        return true
    }

    override fun hashCode(): Int {
        return cords.hashCode()
    }


}

fun main(args: Array<String>) {
    println("Advent of Code Day 15 puzzle 1")

    val pathname = "./working/input_D15.txt"

    val grid = File(pathname)
        .readLines()
        .map {
            it.trim().split("").filter { cell -> cell.isNotBlank() }.map { cell -> cell.toInt() }
        }

    val start = Cell(Point(0, 0), grid[0][0])
    start.findNeighbors(grid, mutableListOf())
    val leastRisk = start.enter().map { it.riskTotal }.minOf { it } - start.riskLevel

    println("Lowest Risk is $leastRisk")
}
