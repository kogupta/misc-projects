package func.prog.java.io

class IO(private val f: () -> Unit) {
    operator fun invoke() = f()
    operator fun plus(other: IO): IO = IO {
        f()
        other.f()
    }

    companion object {
        val empty: IO = IO {}
    }
}