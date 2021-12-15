import java.io.File

fun main(args: Array<String>) {
    println("Advent of Code Day 15 puzzle 2")

    val pathname = "./working/input_D15.txt"

    val grid = File(pathname)
        .readLines()
        .map {
            it.trim().split("").filter { cell -> cell.isNotBlank() }.map { cell -> cell.toInt() }
        }

    var expandedGrid = grid.map {
        it + it.incDigits(1) + it.incDigits(2) + it.incDigits(3) + it.incDigits(4)
    }

    expandedGrid +=
        expandedGrid.map { it.incDigits(1) } +
                expandedGrid.map { it.incDigits(2) } +
                expandedGrid.map { it.incDigits(3) } +
                expandedGrid.map { it.incDigits(4) }

//    expandedGrid.forEach { println(it) }

    val start = Point(0, 0)
    val end = Point(expandedGrid[0].size-1, expandedGrid.size-1)

    println("Adding nodes to map")
    val graphMap = mutableMapOf<Pair<Point, Point>, Int>()
    addToMap(expandedGrid, start, graphMap)

    println("Running dijkstra")
    val shortestPathTree = dijkstra(Graph(graphMap), start)

    println("Finding shortest")
    val shortest = shortestPath(shortestPathTree, start, end)

    println("Calc Risk")
    val leastRisk = shortest.drop(1).map { expandedGrid[it.y][it.x] }.sum()

    println("Lowest Risk is $leastRisk")
}
