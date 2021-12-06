import java.io.File


fun main(args: Array<String>) {
    println("Advent of Code Day 6 puzzle 2")

    var ages = mutableListOf<Long>(0, 0, 0, 0, 0, 0, 0, 0, 0)

    val fish = File("./working/input_D06.txt")
        .readLines()
        .flatMap { it.split(",") }
        .map { it.toInt() }
        .forEach { ages[it] += 1L }

    (0 until 256).forEach { _ ->
        val savedAges = ArrayList(ages)
        (0 until 9).forEach { group ->
            ages[group] = if (group < 8)
                savedAges[group + 1]
            else
                savedAges[0]
        }
        ages[6] += savedAges[0]
    }

    println("Fish count is ${ages.sum()}")
}
