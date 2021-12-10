import java.io.File


private fun pointsFor(terminator: Char): Int = when (terminator) {
    ')' -> 1
    ']' -> 2
    '}' -> 3
    '>' -> 4
    else -> throw Exception("This should never happen!!!")
}


private fun terminatorFor(open: Char): Char = when (open) {
    '(' -> ')'
    '[' -> ']'
    '{' -> '}'
    '<' -> '>'
    else -> throw Exception("This should never happen!!!")
}

private class BadLine: Exception()

private fun stackForLine(line:String): List<Char> {
    val stack = mutableListOf<Char>()

    line.trim().forEach { str ->
        when (str) {
            '(', '[', '{', '<'  -> {
                stack.add(str)
            }
            ')' -> if (stack.last() == '(') {
                stack.removeLast()
            } else {
               throw BadLine()
            }
            ']' ->if (stack.last() == '[') {
                stack.removeLast()
            } else {
                throw BadLine()
            }
            '}' -> if (stack.last() == '{') {
                stack.removeLast()
            } else {
                throw BadLine()
            }
            '>' -> if (stack.last() == '<') {
                stack.removeLast()
            } else {
                throw BadLine()
            }
        }

    }
    return stack
}

private fun reverseStack(stack: List<Char>): List<Char> = stack.reversed().map { terminatorFor(it) }

private fun scoreStack(stack: List<Char>): Long = stack.map { pointsFor(it).toLong() }.reduce { acc, i -> (acc * 5) + i  }

fun main(args: Array<String>) {
    println("Advent of Code Day 10 puzzle 2")


    val points = File("./working/input_D10.txt")
        .readLines()
        .map {
            try {
                stackForLine(it)
            } catch (e:BadLine) {
                emptyList<Char>()
            }
        }
        .filter {
            it.isNotEmpty()
        }
        .map {
            reverseStack(it)
        }
        .map {
            println("Revers is $it")
            scoreStack(it)
        }
        .map {
            println("Score is $it")
            it
        }
        .median()

    println("Middle score is $points")
}
