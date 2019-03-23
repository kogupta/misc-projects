package func.prog.java.tree2

import func.prog.java.recursion.foldLeft
import kotlin.math.max

sealed class Tree<out A : Comparable<@kotlin.UnsafeVariance A>> {
    abstract fun isEmpty(): Boolean
    abstract val size: Int
    abstract val height: Int

    operator fun plus(element: @UnsafeVariance A): Tree<A> = when (this) {
        Empty -> T(Empty, element, Empty)
        is T -> when {
            element < this.value -> this.left.plus(element)
            element > this.value -> this.right.plus(element)
            else -> T(this.left, element, this.right)
        }
    }

    fun contains(a: @UnsafeVariance A): Boolean = when (this) {
        Empty -> false
        is T -> when {
            a < value -> left.contains(a)
            a > value -> right.contains(a)
            else -> value == a
        }
    }

    internal object Empty : Tree<Nothing>() {
        override fun isEmpty(): Boolean = true
        override val size: Int = 0
        override val height: Int = -1
    }

    internal class T<out A : Comparable<@kotlin.UnsafeVariance A>>(
            internal val left: Tree<A>,
            internal val value: A,
            internal val right: Tree<A>
    ) : Tree<A>() {
        override fun isEmpty(): Boolean = false
        override val size: Int = 1 + left.size + right.size
        override val height: Int = 1 + max(left.height, right.height)
    }

    companion object {
        operator fun <A : Comparable<A>> invoke(): Tree<A> = Empty

        operator fun <A : Comparable<A>> invoke(xs: List<A>): Tree<A> =
                xs.foldLeft(Empty as Tree<A>) { tree, a -> tree + a }


    }
}