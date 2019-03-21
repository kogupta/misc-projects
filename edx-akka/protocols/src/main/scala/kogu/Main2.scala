package kogu

import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}

object Main2 {
    sealed trait Greeter
    final case class Greet(whom: String) extends Greeter
    final case object Bye extends Greeter

    val greeter: Behavior[Greeter] = Behaviors.receiveMessage {
        case Greet(whom) =>
            println(s"Hello, $whom")
            Behaviors.same
        case Bye =>
            println("Shutting down ...")
            Behaviors.stopped
    }

    def main(args: Array[String]): Unit = {
        val system = initGuardian()
    }

    private def initGuardian(): ActorSystem[Nothing] = {
        val initializer:Behavior[Nothing] = Behaviors.setup[Nothing]{ ctx =>
            val greeterRef: ActorRef[Greeter] = ctx.spawn(greeter, "greeter")
            ctx.watch(greeterRef)

            greeterRef ! Greet("world!")
            greeterRef ! Bye

            Behaviors.empty
        }
        ActorSystem[Nothing](initializer, "hello-world")
    }
}
