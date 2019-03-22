package func.prog.java.recursion

fun main(args: Array<String>) {
    println(fiboCorecursive(10))
}

fun range2(start: Int, end: Int): List<Int> = unfold(start, { it + 1 }, { it < end })

fun range(start: Int, end: Int): List<Int> {
    val result: MutableList<Int> = mutableListOf()
    var index = start
    while (index < end) {
        result.add(index)
        index++
    }
    return result
}

fun <T> unfold(seed: T, f: (T) -> T, predicate: (T) -> Boolean): List<T> {
    val result: MutableList<T> = mutableListOf()
    var element = seed
    while (predicate(element)) {
        result.add(element)
        element = f(element)
    }
    return result
}

fun <T> unfoldCoRec(seed: T, f: (T) -> T, predicate: (T) -> Boolean): List<T> {
    tailrec fun helper(element: T, acc: List<T>): List<T> =
            if (predicate(element)) {
                helper(f(element), acc + element)
            } else acc

    return helper(seed, listOf())
}

fun <T> iterate(seed: T, f: (T) -> T, n: Int): List<T> {
    tailrec fun helper(element: T, acc: List<T>): List<T> =
            if (acc.size < n) helper(f(element), acc + element)
            else acc

    return helper(seed, listOf())
}

fun fiboCorecursive(number: Int): String {
    val seed = Pair(0, 1)
    val f: (Pair<Int, Int>) -> Pair<Int, Int> = { (first, second) -> Pair(second, first + second) }
    return iterate(seed, f, number + 1)
            .map { it.first }
            .mkString(delim = ", ")
}
