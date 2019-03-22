package func.prog.java.recursion

fun main(args: Array<String>) {
    println("abc".toList().head())

    println(List(1) { 'a' }.mkString())
    println(('a'..'z').asSequence().toList().mkString())

    println("-- fold left --")
    println("Sum of [1..10] : " + sum2((1..10).toList()))
    println(List(1) { 'a' }.mkString2())
    println(('a'..'z').asSequence().toList().mkString2())

    println("-- that's all folds! --")
}

fun <T> List<T>.head(): T {
    require(isNotEmpty()) { "`head` of an empty list" }
    return first()
}

fun <T> List<T>.tail(): List<T> {
    require(isNotEmpty()) { "`tail` of an empty list" }
    return drop(1)
}

fun sum(xs: List<Int>): Int =
        if (xs.isEmpty()) 0
        else xs.head() + sum(xs.tail())

fun sum2(xs: List<Int>): Int = xs.foldLeft(0) { a, b -> a + b }

fun sumCoRec(xs: List<Int>): Int {
    tailrec fun helper(ns: List<Int>, acc: Int): Int =
            if (ns.isEmpty()) acc
            else helper(ns.tail(), ns.head() + acc)

    return helper(xs, 0)
}

fun <T> List<T>.mkString(
        prefix: String = "[",
        delim: String = ",",
        suffix: String = "]"): String {
    tailrec fun helper(xs: List<T>, acc: String): String =
            when {
                xs.isEmpty() -> acc
                xs.tail().isEmpty() -> acc + xs.head()
                else -> helper(xs.tail(), acc + xs.head() + delim)
            }

    return prefix + helper(this, "") + suffix
}

fun <T> List<T>.mkString2(
        prefix: String = "[",
        delim: String = ",",
        suffix: String = "]"): String {
    val s = foldLeft("") { acc, elem ->
        if (acc.isEmpty()) elem.toString()
        else acc + delim + elem.toString()
    }

    return prefix + s + suffix
}

/**
 * z -> zero value aka seed
 */
fun <T, U> List<T>.foldLeft(z: U, f: (U, T) -> U): U {
    tailrec fun helper(xs: List<T>, acc: U): U = when {
        xs.isEmpty() -> acc
        else -> helper(xs.tail(), f(acc, xs.head()))
    }

    return helper(this, z)
}

fun <T, U> List<T>.foldRight(z: U, f: (T, U) -> U): U =
        reversed().foldLeft(z) { u, t -> f(t, u) }
