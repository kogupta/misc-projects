package func.prog.java.recursion

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        println("Hello recursion")

        println(toString("abc".toList()))
        println(toString2("abc".toList()))

        println(sum(10))
    }
}

fun append(s: String, c: Char): String = "$s$c"
fun prepend(c: Char, s: String): String = "$c$s"

fun toString(cs: List<Char>): String {
    tailrec fun toString(cs: List<Char>, s: String): String =
            if (cs.isEmpty()) s
            else toString(cs.drop(1), append(s, cs.first()))

    return toString(cs, "")
}

fun toStringCoRec(cs: List<Char>): String {
    var s = ""
    for (c in cs)
        s = append(s, c)

    return s
}

fun toString2(list: List<Char>): String {
    tailrec fun helper(cs: List<Char>, acc: String): String =
            if (cs.isEmpty())
                acc
            else
                helper(cs.drop(1), prepend(cs.first(), acc))

    return helper(list, "")
}

tailrec fun sum(n: Int): Int = if (n == 1) n else n + sum(n - 1)

fun sum2(n: Int): Int {
    tailrec fun helper(x: Int, acc: Int): Int =
            if (x == 0) acc
            else helper(x - 1, acc + x)

    return helper(n, 0)
}