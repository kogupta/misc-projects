package func.prog.java.io

import arrow.core.Try
import arrow.core.getOrElse
import func.prog.java.recursion.foldLeft
import java.lang.IllegalArgumentException

fun sayHello(name: String): () -> Unit = { println("Hello, $name!") }

fun main(args: Array<String>) {
    val program = sayHello("Georgie baby")
    // evaluate program
    program()

    val computation = show(toString(inverse(0)))
    computation()

    // script
    testScript()
}

fun testScript() {
    fun getName() = "Mickey"

    val instruction1 = IO { print("Hello, ") }
    val instruction2 = IO { print(getName()) }
    val instruction3 = IO { print("!\n") }

    val script: IO = instruction1 + instruction2 + instruction3
    script() // execute script

    val xs: List<IO> = listOf(
            IO { print("Hello, ") },
            IO { print(getName()) },
            IO { print("!\n") }
    )
    val program: IO = xs.foldLeft(IO.empty) { acc, io -> acc + io }
    program() //execute
}

fun show(message: String): IO = IO { println(message) }

fun <A> toString(rd: Try<A>): String =
        rd.map { it.toString() }.getOrElse { rd.toString() }

fun inverse(i: Int): Try<Double> = when (i) {
    0 -> Try.raise(IllegalArgumentException("Div by 0"))
    else -> Try.just(1.0 / i)
}

