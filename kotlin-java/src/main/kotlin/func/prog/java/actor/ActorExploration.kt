package func.prog.java.actor

import arrow.core.Try

interface Actor<T> {
    val context: ActorContext<T>

    fun self(): Try<Actor<T>> = Try.just(this)

    fun tell(message: T, sender: Try<Actor<T>> = self())
    fun tell(message: T, sender: Actor<T>) = tell(message, Try.just(sender))

    fun shutdown()

    companion object {
        fun <T> noSender(): Try<Actor<T>> = TODO()
    }
}

interface MessageProcessor<T> {
    fun process(message: T, sender: Try<Actor<T>>)
}

interface ActorContext<T> {
    fun behavior(): MessageProcessor<T>

    fun become(behavior: MessageProcessor<T>)
}
