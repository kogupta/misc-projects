package func.prog.java.io

import arrow.core.Try
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class ConsoleReader(reader: BufferedReader) : AbstractReader(reader) {
    companion object {
        operator fun invoke(): ConsoleReader =
                ConsoleReader(BufferedReader(InputStreamReader(System.`in`)))
    }
}

class FileReader private constructor(private val reader: BufferedReader) :
        AbstractReader(reader), AutoCloseable {
    override fun close() {
        reader.close()
    }

    companion object {
        operator fun invoke(path: String): Try<FileReader> =
                Try { FileReader(File(path).bufferedReader()) }
    }
}

object ReadFile {
    @JvmStatic
    fun main(args: Array<String>) {
        val path = args[0]

        FileReader(path)
                .map { it.use { fReader -> Person.readFromFile(fReader) } }
                .forEach(::displayPersons, Throwable::printStackTrace)
    }
}

fun displayPersons(xs: List<Person>) {
    xs.forEach { println(it) }
}

object ReadConsole {
    @JvmStatic
    fun main(args: Array<String>) {
        Person.readFromConsole().forEach { println(it) }
    }
}