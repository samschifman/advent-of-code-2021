
enum class Command{
    UP, DOWN, FORWARD
}

data class CourseChange(val command: Command, val magnitude: Int)


data class DiagnosticCounter(
    val countZero: List<Int> = listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
    val countOnes: List<Int> = listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
)

class BingoSquare(val number: Int, var picked:Boolean)

class BingoRow(numbers:List<Int>) {
    private val squares:List<BingoSquare>

    init {
        squares = numbers.map { BingoSquare(it, false) }
    }

    fun mark(draw:Int) = squares.find { it.number == draw }?.let {
            it.picked = true
        }

    fun isMarked(column:Int) = squares[column].picked

    fun isAllMarked() = squares.all { it.picked }

    fun unmarked() = squares.filter { !it.picked }
}

class BingBoard(lines: List<String>, var columnCount: Int = 5) {
    private val rows:List<BingoRow>

    init {
        rows = lines.map { BingoRow(it.split(" ").filter { it.isNotBlank() }.map { it.toInt() }) }
    }

    fun mark(draw: Int) = rows.forEach { it.mark(draw) }

    fun isWinner() = isRowWinner() || isColumnWinner()

    fun score() = rows.flatMap { it.unmarked() }.map { it.number }.sum()

    private fun isRowWinner() = rows.any { it.isAllMarked() }

    private fun isColumnWinner() = (0 until columnCount-1).map { isColumnWinner(it) }.contains(true)

    private fun isColumnWinner(column: Int) = rows.map { it.isMarked(column) }.all { it }
}

data class BingoWinner(val draw: Int, val boardScore: Int)
