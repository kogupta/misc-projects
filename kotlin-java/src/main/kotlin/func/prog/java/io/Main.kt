package func.prog.java.io

import arrow.core.Try
import func.prog.java.recursion.head
import func.prog.java.recursion.tail

typealias Effect<T> = (T) -> Unit

fun main(args: Array<String>) {
    val ra = Try.just(4)
    val rb = Try.just(0)
    val inverse: (Int) -> Try<Double> = { x ->
        when {
            x != 0 -> Try { 1.toDouble() / x }
            else -> Try.raise(IllegalArgumentException("Division by 0"))
        }
    }

    val showResult: (Double) -> Unit = ::println
    val showError: Effect<Throwable> = { println("Error - ${it.message}") }

    val rt1 = ra.flatMap(inverse)
    val rt2 = rb.flatMap(inverse)

    print("Inverse of 4: ")
    rt1.forEach(showResult, showError)

    System.out.print("Inverse of 0: ")
    rt2.forEach(showResult, showError)
}

fun <T> Try<T>.forEach(onSuccess: Effect<T>, onFailure: Effect<Throwable>) {
    when (this) {
        is Try.Success -> onSuccess(value)
        is Try.Failure -> onFailure(exception)
    }
}

fun <T> List<T>.forEach(effect: Effect<T>) {
    tailrec fun go(xs: List<T>) {
        if (!xs.isEmpty()) {
            effect(xs.head())
            go(xs.tail())
        }
    }
    when {
        isEmpty() -> {}
        else -> go(this)
    }
}
