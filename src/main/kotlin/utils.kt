
fun List<List<Int>>.mostCommonAt(index:Int): Int {
    var countOne = this.count { it[index] == 1 }
    var countZero = this.count { it[index] == 0 }
    return if (countOne >= countZero) 1 else 0
}


fun List<List<Int>>.leastCommonAt(index:Int): Int {
    var countOne = this.count { it[index] == 1 }
    var countZero = this.count { it[index] == 0 }
    return if (countOne < countZero) 1 else 0
}

fun List<List<Int>>.filterAt(index:Int, expected:Int): List<List<Int>> = this.filter { (it[index] == expected) }

fun List<Int>.median() = this.sorted().let {
    if (it.size % 2 == 0)
        (it[it.size / 2] + it[(it.size - 1) / 2]) / 2
    else
        it[it.size / 2]
}

fun List<Long>.median() = this.sorted().let {
    if (it.size % 2 == 0)
        (it[it.size / 2] + it[(it.size - 1) / 2]) / 2
    else
        it[it.size / 2]
}

fun Int.partialSum(): Int = (this * (this+1))/2


fun <Point> List<Pair<Point, Point>>.getUniqueValuesFromPairs(): Set<Point> = this
    .map { (a, b) -> listOf(a, b) }
    .flatten()
    .toSet()

fun <Point> List<Pair<Point, Point>>.getUniqueValuesFromPairs(predicate: (Point) -> Boolean): Set<Point> = this
    .map { (a, b) -> listOf(a, b) }
    .flatten()
    .filter(predicate)
    .toSet()


fun List<Int>.incDigits(step:Int = 1) = this.map { if (it + step > 9) (it + step) - 9 else it + step }