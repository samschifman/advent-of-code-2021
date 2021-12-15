import java.io.File

class LetterCombo(val firstCombo:String, val secondCombo:String, var count:Long = 0, var increment:Long = 0) {
    override fun toString(): String {
        return "LetterCombo(firstCombo='$firstCombo', secondCombo='$secondCombo', count=$count, increment=$increment)"
    }
}

fun main(args: Array<String>) {
    println("Advent of Code Day 14 puzzle 1")

    val pathname = "./working/input_D14.txt"

    var template = File(pathname)
        .readLines()
        .first()
        .split("")
        .filter { it.isNotBlank() }


    var mappings = File(pathname)
        .readLines()
        .filter {
            it.isNotBlank() && it.contains("->")
        }
        .map {
            it
                .split(" -> ")
                .filter { it.isNotBlank() }
        }
        .map {
            val letters = it[0].split("").filter { it.isNotBlank() }
            it[0] to LetterCombo(
                firstCombo = "${letters[0]}${it[1]}",
                secondCombo = "${it[1]}${letters[1]}"
            )
        }
        .toMap()
        .toMutableMap()

    template
        .windowed(2)
        .forEach { mappings["${it[0]}${it[1]}"]!!.count = mappings["${it[0]}${it[1]}"]!!.count + 1 }

//    println("Step -1 is $mappings")

    (0..39).forEach {
        mappings.forEach {
            if (it.value.count > 0) {
                mappings[it.value.firstCombo]!!.increment += it.value.count
                mappings[it.value.secondCombo]!!.increment += it.value.count
                it.value.increment -= it.value.count
            }
        }

        mappings.forEach {
            it.value.count += it.value.increment
            it.value.increment = 0
        }
        // println("Step $it is $mappings")
    }

    val counts = mutableMapOf<String, Long>()

    mappings.forEach {
        val letters = it.key.split("").filter { letter -> letter.isNotBlank() }
        counts[letters[0]] = counts[letters[0]]?.plus(it.value.count) ?: it.value.count
    }


    println("End is $mappings")

    val max = counts.maxOf { it.value }
    val min = counts.minOf { it.value }

    val count = max-(min+1)
    println("Answer is ${count}")
}

/*
fun main(args: Array<String>) {
    println("Advent of Code Day 14 puzzle 1")

    val pathname = "./working/input_D14.txt"
    val inFilename = "./working/scratch/in.txt";
    val outFilename = "./working/scratch/out.txt";

    var template = File(pathname)
        .readLines()
        .first()
        .split("")
        .filter { it.isNotBlank() }

    // File(inFilename).writeText(template.joinToString(""))

    val mappings = File(pathname)
        .readLines()
        .filter {
            it.isNotBlank() && it.contains("->")
        }
        .map {
            it
                .split(" -> ")
                .filter { it.isNotBlank() }
        }
        .map {
            it[0] to it[1]
        }
        .toMap()

    (0..39).forEach {

        val inReader = File(inFilename).bufferedReader()
        val outWriter = File(outFilename).bufferedWriter()

        var next = inReader.read()
        var last = -1

        while(next > -1) {
            last = next;
            next = inReader.read()
            if (next > -1) {
                outWriter.write(last)
                val insert = mappings["${last.toChar()}${next.toChar()}"]
                outWriter.write(insert)
            }
        }
        outWriter.write(last)
        inReader.close()
        outWriter.flush()
        outWriter.close()

        File(inFilename).delete()
        File(outFilename).renameTo(File(inFilename))

        print(" $it ")
//        println("Step $it is ${template.joinToString("")}")
//        println("Step $it dist is ${template.groupBy { it }.map { it.key to it.value.size }.sortedBy { it.second }}")
//        val max = template.groupBy { it }.map { it.value.size }.maxOf { it }
//        val min = template.groupBy { it }.map { it.value.size }.minOf { it }
//
//        println("Step $it Answer is ${max-min}")
    }
    println()

//    val max = template.groupBy { it }.map { it.value.size }.maxOf { it }
//    val min = template.groupBy { it }.map { it.value.size }.minOf { it }
//
//    println("Answer is ${max-min}")
}


/*
fun main(args: Array<String>) {
    println("Advent of Code Day 14 puzzle 2")

    // OSCKBNPFVH
    val letterMap = mapOf<String, Byte>( "B" to 0, "C" to 1, "F" to 2, "H" to 3, "K" to 4, "N" to 5, "O" to 6, "P" to 7, "S" to 8, "V" to 9 )

    val pathname = "./working/input_D14.txt"

    val inFilename = "./working/scratch/in.txt";
    val outFilename = "./working/scratch/out.txt";

    var template = File(pathname)
        .readLines()
        .first()
        .split("")
        .filter { it.isNotBlank() }
        .map { letterMap[it]!! }

    File(inFilename).writeText(template.joinToString { "" })


    val mappings = File(pathname)
        .readLines()
        .filter {
            it.isNotBlank() && it.contains("->")
        }
        .map {
            it
                .split(" -> ")
                .filter { it.isNotBlank() }
        }
        .map {
            val keyLetters = it[0].split("").filter { it.isNotBlank() }
            val key = letterMap[keyLetters[0]]!! * 10 + letterMap[keyLetters[1]]!!
            key to letterMap[it[1]]!!
        }
        .toMap()


    println(template.joinToString(""))
    (0..1).forEach { it ->
        val inReader = File(inFilename).bufferedReader()
        val outWriter = File(outFilename).bufferedWriter()

        var next = inReader.read()
        var last = -1
        var zero = '0'.code

        while(next > -1) {
            last = next;

            outWriter.write(last.toString())
            val insert = mappings[last.minus(zero) * 10 * next.minus(zero)]
            outWriter.write(insert.toString())
        }
        outWriter.write(last.toString())
        inReader.close()
        outWriter.flush()
        outWriter.close()


//        template = template
//            .windowed(2)
//            .flatMap { pair ->
//                val insert = mappings.get((pair[0] * 10) + pair[1])!!
//                listOf(pair[0], insert)
//            }
//            .plus(template.last())
//
//        // print(" $it ")
//        println("Step $it is ${template.joinToString("")}")
//        println("Step $it dist is ${template.groupBy { it }.map { it.key to it.value.size }.sortedBy { it.second }}")
//        val max = template.groupBy { it }.map { it.value.size }.maxOf { it }
//        val min = template.groupBy { it }.map { it.value.size }.minOf { it }
//
//        println("Step $it Answer is ${max-min}")
    }

    val max = template.groupBy { it }.map { it.value.size }.maxOf { it }
    val min = template.groupBy { it }.map { it.value.size }.minOf { it }

    println("Answer is ${max-min}")
}



//    val mappingsLetters = File(pathname)
//        .readLines()
//        .filter {
//            it.isNotBlank() && it.contains("->")
//        }
//        .map {
//            it
//                .split(" -> ")
//                .filter { it.isNotBlank() }
//        }
//        .map {
//            it[0] to it[1]
//        }
//        .toMap()
//    println(mappingsLetters.toList().sortedBy { it.first })

 */