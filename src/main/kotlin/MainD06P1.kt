import java.io.File


fun main(args: Array<String>) {
    println("Advent of Code Day 6 puzzle 1")

    val fish = File("./working/input_D06.txt")
        .readLines()
        .flatMap { it.split(",") }
        .map { Lanternfish(it.toInt()) }
        .toMutableList()

    (0 until 80).forEach { _ ->
        fish.map { it.addDay() }.filter { it }.forEach { _ -> fish.add(Lanternfish()) }
    }

    val totalAge = fish.sumOf { it.age }

    println("Total age is ${totalAge}, Fish count is ${fish.size}")
}
