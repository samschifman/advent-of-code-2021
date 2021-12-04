
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