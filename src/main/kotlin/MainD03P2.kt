import java.io.File

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

fun main(args: Array<String>) {
    println("Advent of Code Day 3 puzzle 2")

    /*
     * THIS IS NOT THE MOST ELEGANT SOLUTION:
     *
     * I needed to get this done today, so I just solved it without
     * trying to find the most elegant way. Not that others are the
     * most elegant, but other days are more elegant than this!
     */

    var oxygenList =
        File("./working/input_D03.txt")
            .readLines()
            .map { it.map { if (it == '1') 1 else 0 } }

    var index = 0
    while (oxygenList.size > 1) {
        oxygenList = oxygenList.filterAt(index, oxygenList.mostCommonAt(index))
        index++
    }

    val oxygen = oxygenList[0].map { it.toString() }.joinToString("").toInt(2)

    var co2List =
        File("./working/input_D03.txt")
            .readLines()
            .map { it.map { if (it == '1') 1 else 0 } }

    index = 0
    while (co2List.size > 1) {
        co2List = co2List.filterAt(index, co2List.leastCommonAt(index))
        index++
    }

    val co2 = co2List[0].map { it.toString() }.joinToString("").toInt(2)

    println("Oxygen is $oxygen, CO2 is $co2, Answer is ${oxygen * co2}")
}