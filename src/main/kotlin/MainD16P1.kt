import java.io.File
import java.math.BigInteger



fun main(args: Array<String>) {
    println("Advent of Code Day 16 puzzle 1")

    val pathname = "./working/input_D16.txt"

    val hexMap = mapOf(
        "0" to "0000", "1" to "0001", "2" to "0010", "3" to "0011", "4" to "0100", "5" to "0101", "6" to "0110", "7" to "0111",
        "8" to "1000", "9" to "1001", "A" to "1010", "B" to "1011", "C" to "1100", "D" to "1101", "E" to "1110", "F" to "1111"
    )

    val bytes = File(pathname)
        .readLines()
        .first()
        .split("")
        .filter { it.isNotBlank() }
        .map {
            hexMap[it]!!
        }
        .flatMap { it.split("") }
        .filter { it.isNotBlank() }

    val root = Packet.parsePacket(bytes).second

    val totalVersion = root.totalVersions()

    println("Total is $totalVersion")
}
