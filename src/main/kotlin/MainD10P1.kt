import java.io.File

private fun pointsFor(opener: String): Int = when (opener) {
    "(" -> 3
    "[" -> 57
    "{" -> 1197
    "<" -> 25137
    else -> throw Exception("This should never happen!!!")
}

private fun test(stack: MutableList<String>, expected: String): Int = if (stack.last() == expected) {
    stack.removeLast()
    0
} else {
    pointsFor(expected)
}

fun main(args: Array<String>) {
    println("Advent of Code Day 10 puzzle 1")

    val points = File("./working/input_D10.txt")
        .readLines()
        .map {
            it.trim()
                .split("")
        }
        .map {
            val stack = mutableListOf<String>()
            it.map { str ->
                when (str) {
                    "(", "[", "{", "<"  -> {
                        stack.add(str)
                        0
                    }
                    ")" -> test(stack, "(")
                    "]" -> test(stack, "[")
                    "}" -> test(stack, "{")
                    ">" -> test(stack, "<")
                    else -> 0
                }

            }.find { term -> term > 0 } ?: 0

        }
        .sumOf {
            println("line is $it")
            it
        }

    println("Points is $points")
}
