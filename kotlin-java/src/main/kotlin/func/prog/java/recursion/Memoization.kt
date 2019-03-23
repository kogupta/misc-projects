package func.prog.java.recursion

fun main(args: Array<String>) {
    println("eval double 2: " + Doubler.double(2))
    val f: (Int) -> Int = {
        print("starting ... eval($it) ... ")
        Thread.sleep(10_000)
        println("done!")
        it * 2
    }
    val fn = Memoizer.memoize(f)
    println(fn(4))
    println(fn(10))
    println(fn(20))
    println(fn(20))

    val f2: (Int) -> Int = {
        print("starting ... eval($it) ... ")
        Thread.sleep(10_000)
        println("done!")
        -it
    }
    val fn2 = Memoizer.memoize(f2)
    println(fn2(4))
    println(fn2(10))
    println(fn2(20))
    println(fn2(20))
}

object Doubler {
    private val cache: MutableMap<Int, Int> = mutableMapOf()

    fun double(n: Int): Int = cache.computeIfAbsent(n) { 2 * it }
}
