import java.io.File


fun main(args: Array<String>) {
    println("Advent of Code Day 12 puzzle 1")

    val caves = mutableSetOf<Cave>()

    File("./working/input_D12.txt")
        .readLines()
        .map {
            it.trim().split("-")
        }
        .forEach {
            val cave0 = caves.find { cave -> cave.name == it[0] }?: Cave(it[0])
            val cave1 = caves.find { cave -> cave.name == it[1] }?: Cave(it[1])
            caves.add(cave0)
            caves.add(cave1)
            cave0.tunnels.add(cave1)
            cave1.tunnels.add(cave0)
        }

    caves.forEach { println(it) }

    val start = caves.find { it.name == "start" }!!
    val paths = start.enter()

    println("Paths is ${paths.size}")
}
