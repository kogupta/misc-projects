package func.prog.java.io

import arrow.core.Try
import java.io.BufferedReader
import java.io.Closeable

interface Input : Closeable {
    fun readString(): Try<Pair<String, Input>>
    fun readInt(): Try<Pair<Int, Input>>

    fun readString(message: String): Try<Pair<String, Input>> {
        println(message)
        return readString()
    }

    fun readInt(message: String): Try<Pair<Int, Input>> {
        println(message)
        return readInt()
    }
}

abstract class AbstractReader(private val reader: BufferedReader) : Input {
    override fun readString(): Try<Pair<String, Input>> =
            Try {
                val line = reader.readLine()
                Pair(line, this)
            }

    override fun readInt(): Try<Pair<Int, Input>> =
            Try {
                val line = reader.readLine()
                Pair(line.toInt(), this)
            }

    override fun close(): Unit = reader.close()
}

fun main(args: Array<String>) {
    val input = ConsoleReader()
    val rString = input.readString("Enter your name:").map { t -> t.first }
    val nameMessage = rString.map { "Hello, $it!" }
    nameMessage.forEach(::println, onFailure = { println(it.message) })

    val rInt = input.readInt("Enter your age:").map { t -> t.first }
    val ageMessage = rInt.map { "You look younger than $it!" }
    ageMessage.forEach(::println, onFailure = { println("Invalid age. Please enter an integer") })
}
