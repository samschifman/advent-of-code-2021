import java.io.File



fun main(args: Array<String>) {
    println("Advent of Code Day 14 puzzle 1")

    val pathname = "./working/input_D14.txt"

    var template = File(pathname)
        .readLines()
        .first()
        .split("")
        .filter { it.isNotBlank() }


    val mappings = File(pathname)
        .readLines()
        .filter {
            it.isNotBlank() && it.contains("->")
        }
        .map {
            it
                .split(" -> ")
                .filter { it.isNotBlank() }
        }
        .map {
            it[0] to it[1]
        }
        .toMap()

    println(template.joinToString(""))
    (0..9).forEach {
        template = template
            .windowed(2)
            .flatMap {
                val insert = mappings.get(it.joinToString(""))!!
                listOf(it[0], insert)
            }
            .plus(template.last())

        println("Step $it is ${template.take(2).joinToString("")} xxx ${template.takeLast(2).joinToString("")}")
        println("Step $it dist is ${template.groupBy { it }.map { it.key to it.value.size }.sortedBy { it.second }}")
        val max = template.groupBy { it }.map { it.value.size }.maxOf { it }
        val min = template.groupBy { it }.map { it.value.size }.minOf { it }

        println("Step $it Answer is ${max-min}")
    }

    val max = template.groupBy { it }.map { it.value.size }.maxOf { it }
    val min = template.groupBy { it }.map { it.value.size }.minOf { it }

    println("Answer is ${max-min}")
}
