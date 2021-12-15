import java.io.File



fun main(args: Array<String>) {
    println("Advent of Code Day 15 puzzle 1B")

    val pathname = "./working/input_D15.txt"

    val grid = File(pathname)
        .readLines()
        .map {
            it.trim().split("").filter { cell -> cell.isNotBlank() }.map { cell -> cell.toInt() }
        }

    val start = Point(0, 0)
    val end = Point(grid[0].size-1, grid.size-1)

    val graphMap = mutableMapOf<Pair<Point, Point>, Int>()
    addToMap(grid, start, graphMap)

    val shortestPathTree = dijkstra(Graph(graphMap), start)

    val shortest = shortestPath(shortestPathTree, start, end)

    val leastRisk = shortest.drop(1).map { grid[it.y][it.x] }.sum()

    println("Lowest Risk is $leastRisk")
}
