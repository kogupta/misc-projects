package func.prog.java.recursion

import java.util.concurrent.ConcurrentHashMap

class Memoizer<T, U> private constructor() {
    private val cache = ConcurrentHashMap<T, U>()

    private fun doMemoize(f: (T) -> U): (T) -> U = { t ->
        cache.computeIfAbsent(t) { f(it) }
    }

    companion object {
        fun <T, U> memoize(function: (T) -> U): (T) -> U =
                Memoizer<T, U>().doMemoize(function)
    }

}