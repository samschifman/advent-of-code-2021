import java.io.File


fun main(args: Array<String>) {
    println("Advent of Code Day 4 puzzle 1")

    val lines = File("./working/input_D04.txt").readLines()

    val draws = lines[0].split(",").map { it.toInt() }

    val boardLines = lines.drop(2)

    val boards = boardLines.windowed(5, 6).map { BingBoard(it) }

    var winner:BingoWinner? = null
    for (draw in draws) {
        boards.forEach { it.mark(draw) }

        winner = boards.find { it.isWinner() }?.let { BingoWinner(draw, it.score()) }

        if (winner != null) {
            break
        }
    }


    if (winner != null) {
        println("Winning Draw is ${winner.draw}, Board score is ${winner.boardScore}, Answer is ${winner.draw * winner.boardScore}")
    } else {
        println("No winner")
    }
}