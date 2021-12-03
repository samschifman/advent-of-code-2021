import java.io.File



fun main(args: Array<String>) {
    println("Advent of Code Day 3 puzzle 1")

    val diagnosticCounter =
        File("./working/input_D03.txt")
            .readLines()
            .fold(DiagnosticCounter()) { lastDiagnosticCounter, line ->
                line.foldIndexed(lastDiagnosticCounter.copy()) { index, lineDiagnosticCounter, c ->
                    lineDiagnosticCounter.copy(
                        countZero = lineDiagnosticCounter.countZero.mapIndexed<Int, Int> { countIndex, value ->
                            if (index == countIndex && c == '0') value + 1 else value
                        },
                        countOnes = lineDiagnosticCounter.countOnes.mapIndexed<Int, Int> { countIndex, value ->
                            if (index == countIndex && c == '1') value + 1 else value
                        }
                    )
                }
            }

    val gamaBinaryString =
        diagnosticCounter.countOnes.mapIndexed { index, ones ->  if (ones > diagnosticCounter.countZero[index]) "1" else "0" }.joinToString("")
    val gamaRate = gamaBinaryString.toInt(2)

    val epsilonBinaryString = gamaBinaryString.map { if (it == '0') '1' else '0' }.joinToString("")
    val epsilonRate = epsilonBinaryString.toInt(2)

    println("Gama is $gamaRate, Epsilon is $epsilonRate, Answer is ${gamaRate * epsilonRate}")
}