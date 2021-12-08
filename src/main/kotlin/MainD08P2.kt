import java.io.File

private fun findNumber(row: String): Int {
    // Single letters:
    // 1 is 2 letters
    // 4 is 4 letters
    // 7 is 3 letters
    // 8 is 7 letters
    // 5 letter:
    // 5 is the one that contains 2 letters in 4 but not in 7 or 1
    // 3 must contain all of 1
    // 2 is the remaining 5 letter
    // 6 letter:
    // 0 does not contain a letter that is in 2, 3, 4, 5
    // 9 contains a letter in 4 and 5
    // 6 is the remaining 6 letter

    val numbers = mutableListOf<Set<Char>>( emptySet(), emptySet(), emptySet(), emptySet(), emptySet(), emptySet(), emptySet(), emptySet(), emptySet(), emptySet())
    val numberPatterns = row.split("|")[0].trim().split(" ").toMutableList()

    numbers[1] = numberPatterns.find { it.length == 2 }!!.trim().toSet()
    numberPatterns.remove(numbers[1].joinToString(""))
    numbers[4] = numberPatterns.find { it.length == 4 }!!.trim().toSet()
    numberPatterns.remove(numbers[4].joinToString(""))
    numbers[7] = numberPatterns.find { it.length == 3 }!!.trim().toSet()
    numberPatterns.remove(numbers[7].joinToString(""))
    numbers[8] = numberPatterns.find { it.length == 7 }!!.trim().toSet()
    numberPatterns.remove(numbers[8].joinToString(""))

    // 5 letters
    numbers[5] = numberPatterns.find { it.length == 5 && it.trim().toList().containsAll(numbers[4].subtract(numbers[1])) }!!.trim().toSet()
    numberPatterns.remove(numbers[5].joinToString(""))
    numbers[3] = numberPatterns.find { it.length == 5 && it.trim().toList().containsAll(numbers[1]) }!!.trim().toSet()
    numberPatterns.remove(numbers[3].joinToString(""))
    numbers[2] = numberPatterns.find { it.length == 5 }!!.trim().toSet()
    numberPatterns.remove(numbers[2].joinToString(""))

    // 6 letters
    numbers[0] = numberPatterns.find {
        it.length == 6 &&
                !it.trim().toList().containsAll( numbers[2].intersect(numbers[3]).intersect(numbers[4]).intersect(numbers[5]) )
    }!!.trim().toSet()
    numberPatterns.remove(numbers[0].joinToString(""))
    numbers[9] = numberPatterns.find {
        it.length == 6 &&
                it.trim().toList().containsAll( numbers[4].plus(numbers[5]) )
    }!!.trim().toSet()
    numberPatterns.remove(numbers[9].joinToString(""))
    numbers[6] = numberPatterns[0].trim().toSet()

    val digitPattern = row.split("|")[1].trim().split(" ")
    var numberAsString = digitPattern.map {
        numbers.indexOf(it.trim().toSet()).toString()
    }.joinToString("")

    printNumbers(numbers)
    println("Number for row is ${numberAsString}")

    return numberAsString.toInt()
}

private fun printNumbers(numbers: List<Set<Char>>) {
    numbers.forEachIndexed { index, chars -> print("${index} is ${chars}, ") }
    println()
}


fun main(args: Array<String>) {
    println("Advent of Code Day 8 puzzle 2")

    val totalNumber = File("./working/input_D08.txt")
        .readLines()
        .map {
            findNumber(it)
        }
        .sum()


    println("Total Number is ${totalNumber}")
}
