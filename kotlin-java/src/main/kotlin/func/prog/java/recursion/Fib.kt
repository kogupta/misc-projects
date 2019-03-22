package func.prog.java.recursion

import kotlin.system.measureNanoTime

fun main(args: Array<String>) {
    (0 until 10).forEach { print("${fib(it)} ") }
    println()
    (0 until 10).forEach { print("${fib2(it)} ") }
    println()

    println("---- naive impl ----")
    measure { fib(it) }
    println("---- corecursive -----")
    measure { fib2(it) }
    println("---- tail rec -----")
    measure { fib3(it) }
}

private fun measure(block: (Int) -> Int) {
    for (n in 10..40 step 2) {
        val time = String.format("%,d", measureNanoTime { block(n) })
        println("$n => $time")
    }
}

fun fib(n: Int): Int =
        if (n == 0 || n == 1) 1
        else fib(n - 1) + fib(n - 2)

fun fib2(n: Int): Int {
    fun helper(acc1: Int, acc2: Int, x: Int): Int = when (x) {
        0 -> 1
        1 -> acc1 + acc2
        else -> helper(acc2, acc1 + acc2, x - 1)
    }

    return helper(0, 1, n)
}

fun fib3(n: Int): Int {
    tailrec fun helper(acc1: Int, acc2: Int, x: Int): Int = when (x) {
        0 -> 1
        1 -> acc1 + acc2
        else -> helper(acc2, acc1 + acc2, x - 1)
    }

    return helper(0, 1, n)
}
