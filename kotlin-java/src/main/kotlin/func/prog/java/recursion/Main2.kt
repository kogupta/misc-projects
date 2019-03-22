package func.prog.java.recursion

import kotlin.system.measureNanoTime

object Main2 {
    @JvmStatic
    fun main(args: Array<String>) {
        println("Hello from kotlin - again")
        assert(addCoRec(0, 0) == 0)
        assert(addCoRec(0, 20) == 20)
        assert(addCoRec(10, 0) == 10)
        assert(addCoRec(10, 20) == 30)

        assert(factorial(1) == 1)
        assert(factorial(2) == 2)
        assert(factorial(3) == 6)
        assert(factorial(4) == 24)
        assert(factorial(5) == 120)
        assert(factorial(6) == 720)

        for (n in 10..30 step 2) {
            println(String.format("%,d", measureNanoTime { factorial2(n) }))
            println(String.format("%,d", measureNanoTime { factorial(n) }))
            println()
        }
    }
}

fun inc(n: Int) = n + 1
fun dec(n: Int) = n - 1

fun addCoRec(a: Int, b: Int): Int {
    fun helper(x: Int, acc: Int): Int =
            if (x == 0) acc
            else helper(dec(x), inc(acc))

    return helper(a, b)
}

fun factorial(n: Int): Int {
    fun helper(x: Int, acc: Int): Int =
            if (x == 0 || x == 1) acc
            else helper(x - 1, x * acc)

    return helper(n, 1)
}

fun factorial2(n: Int): Int {
    tailrec fun helper(x: Int, acc: Int): Int =
            if (x == 0 || x == 1) acc
            else helper(x - 1, x * acc)

    return helper(n, 1)
}

