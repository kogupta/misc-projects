package func.prog.java.actor

import arrow.core.Try
import func.prog.java.io.forEach
import java.util.concurrent.Semaphore

object PingPong {
    private val semaphore = Semaphore(1)

    @JvmStatic
    fun main(args: Array<String>) {
        val referee = object : AbstractActor<Int>("Referee") {
            override fun onReceive(message: Int, sender: Try<Actor<Int>>) {
                println("Game ended after $message shots")
                semaphore.release()
            }
        }

        val player1 = player("Player1", "Ping", referee)
        val player2 = player("Player2", "Pong", referee)

        semaphore.acquire()

        player1.tell(1, Try.just(player2))
        semaphore.acquire()
    }
}

fun player(id: String, sound: String, referee: Actor<Int>) = object : AbstractActor<Int>("id") {
    override fun onReceive(message: Int, sender: Try<Actor<Int>>) {
        println("$sound - $message")
        if (message >= 10) {
            referee.tell(message, sender)
        } else {
            sender.forEach(
                    { actor: Actor<Int> -> actor.tell(message + 1, self())},
                    { referee.tell(message, sender) }
            )
        }
    }
}
