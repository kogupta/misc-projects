package actorbintree

import akka.actor.{Actor, ActorSystem, Props}
import BinaryTreeSet._
import akka.event.LoggingReceive

class DriverActor extends Actor {
  def receive = LoggingReceive {
    case msg: String => println("hello " + msg)
    case ContainsResult(id, result) => println("ContainsResult: " + result)
    case OperationFinished(id) => println("OperationFinished: " + id)
    case _ => println("unexpected message.!!")
  }
}

object Driver {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("niuniu-akka")
    val binTree = system.actorOf(Props(classOf[BinaryTreeSet]), "binaryTree")
    val driverRef = system.actorOf(Props(classOf[DriverActor]), "driver")
//    driverRef ! "test"
    binTree ! Contains(driverRef, 1, 1)
    binTree ! Insert(driverRef, 2, 1)
    binTree ! Contains(driverRef, 3, 1)

    Thread.sleep(10000)
    system.terminate()
  }
}