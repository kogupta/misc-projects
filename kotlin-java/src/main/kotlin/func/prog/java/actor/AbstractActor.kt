package func.prog.java.actor

import arrow.core.Try
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors.newSingleThreadExecutor
import java.util.concurrent.RejectedExecutionException
import java.util.concurrent.ThreadFactory

abstract class AbstractActor<T>(protected val id: String) : Actor<T> {
    private val executor: ExecutorService = newSingleThreadExecutor(DaemonThreadFactory())

    override val context: ActorContext<T> = object: ActorContext<T> {
        var behavior: MessageProcessor<T> = object: MessageProcessor<T> {
            override fun process(message: T, sender: Try<Actor<T>>) = onReceive(message, sender)
        }

        @Synchronized
        override fun become(behavior: MessageProcessor<T>) {
            this.behavior = behavior
        }

        override fun behavior() = behavior
    }

    override fun self(): Try<Actor<T>> = Try.just(this)

    override fun shutdown() = this.executor.shutdown()

    @Synchronized
    override fun tell(message: T, sender: Try<Actor<T>>) {
        executor.execute {
            try {
                context.behavior().process(message, sender)
            } catch (e: RejectedExecutionException) {
                /*
                 * This is probably normal and means all pending tasks
                 * were canceled because the actor was stopped.
                 */
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }

    abstract fun onReceive(message: T, sender: Try<Actor<T>>)
}

internal class DaemonThreadFactory : ThreadFactory {
    override fun newThread(r: Runnable): Thread {
        val t = Thread(r)
        t.isDaemon = true
        t.name = "ActorContextPool"
        return t
    }
}
