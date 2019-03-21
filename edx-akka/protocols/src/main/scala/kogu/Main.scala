package kogu

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object Main {
    val greeter: Behavior[String] = Behaviors.receiveMessage { whom =>
        println(s"Hello, $whom")
        Behaviors.stopped
    }

    def main(args: Array[String]): Unit = {
        val system = ActorSystem(greeter, "hello-world-guardian")
        system.tell("world?")
    }
}
