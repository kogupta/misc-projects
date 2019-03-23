package func.prog.java.io

import arrow.core.Try

data class Person(val id: Int, val firstName: String, val lastName: String) {
    companion object {
        fun parse(input: Input): Try<Pair<Person, Input>> =
                input.readInt("Enter id: ").flatMap { id ->
                    input.readString("Enter first name: ").flatMap { fName ->
                        input.readString("Enter last name: ").map { lName ->
                            Pair(Person(id.first, fName.first, lName.first), input)
                        }
                    }
                }

        fun readFromConsole(): List<Person> =
                unfold3(ConsoleReader(),
                        { input -> parse(input) },
                        { pair -> pair.first })

        fun readFromFile(reader: FileReader): List<Person> {
            return unfold3(reader,
                    { input -> parse(input) },
                    { pair -> pair.first })
        }
    }
}

fun <T, TEMP, U> unfold3(seed: T,
                         generator: (T) -> Try<TEMP>,
                         mapper: (TEMP) -> U): List<U> {
    fun go(acc: List<U>): List<U> {
        val element = generator(seed)
        return when (element) {
            is Try.Failure -> acc
            is Try.Success -> go(acc + mapper(element.value))
        }
    }

    return go(listOf())
}
