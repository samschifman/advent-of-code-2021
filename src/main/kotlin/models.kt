
enum class Command{
    UP, DOWN, FORWARD
}

data class CourseChange(val command: Command, val magnitude: Int)


data class DiagnosticCounter(
    val countZero: List<Int> = listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
    val countOnes: List<Int> = listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
)