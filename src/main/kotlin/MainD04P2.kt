import java.io.File


fun main(args: Array<String>) {
    println("Advent of Code Day 4 puzzle 2")

    val lines = File("./working/input_D04.txt").readLines()

    val draws = lines[0].split(",").map { it.toInt() }

    val boardLines = lines.drop(2)

    var boards = boardLines.windowed(5, 6).map { BingBoard(it) }

    var winner: BingoWinner? = null
    for (draw in draws) {
        boards.forEach { it.mark(draw) }

        if (boards.size > 1) {
            boards = boards.filter { !it.isWinner() }
        } else if (boards[0].isWinner()) {
            winner = BingoWinner(draw, boards[0].score())
        }

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