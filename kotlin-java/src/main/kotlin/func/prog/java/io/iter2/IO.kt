package func.prog.java.io.iter2

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.IllegalStateException

class IO<out A>(private val f: () -> A) {
    operator fun invoke() = f()

//    operator fun plus(other: IO): IO = IO {
//        f()
//        other.f()
//    }

    fun <B> map(fn: (A) -> B): IO<B> = IO { fn(f()) }
//    fun <B> map(fn: (A) -> B): IO<B> = IO { fn(this()) }
    fun <B> flatMap(fn: (A) -> IO<B>): IO<B> = fn(f())
//    fun <B> flatMap(fn: (A) -> IO<B>): IO<B> = IO { fn(this())() }

    companion object {
        val empty: IO<Unit> = IO {}

        operator fun <A> invoke(a: A): IO<A> = IO { a }

        fun <A> repeat(n: Int, io: IO<A>): IO<List<A>> = TODO()
    }
}

object Console {
    private val br = BufferedReader(InputStreamReader(System.`in`))

    fun readline(): IO<String> = IO {
        try {
            br.readLine()
        } catch (e: IOException) {
            throw IllegalStateException(e)
        }
    }

    fun println(o: Any): IO<Unit> = IO { kotlin.io.println(o.toString()) }

    fun print(o: Any): IO<Unit> = IO { kotlin.io.print(o.toString()) }
}

fun main(args: Array<String>) {
    val script = sayHello()
    script()

    println("----")

    val script2 = sayHello2()
    script2()
}

private fun sayHello(): IO<Unit> = Console.print("Enter your name: ")
        .map { Console.readline()() }
        .map { it -> buildMessage(it) }
        .map { Console.println(it)() }

private fun sayHello2(): IO<Unit> = Console.print("Enter your name: ")
        .flatMap { Console.readline() }
        .map { it -> buildMessage(it) }
        .flatMap { Console.println(it) }

private fun buildMessage(name: String): String = "Hello, $name!"