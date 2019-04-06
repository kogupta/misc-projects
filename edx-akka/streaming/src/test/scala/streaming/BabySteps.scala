package streaming

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, RunnableGraph, Sink, Source}

import scala.concurrent.Future

object BabySteps {
  def main(args: Array[String]): Unit = {
    println("hello world")
  }

  private def example(): Unit = {
    val system: ActorSystem = ActorSystem()
    val mat: ActorMaterializer = ActorMaterializer()(system)

    val eventualInt: Future[Int] = Source(1 to 10)
      .map(_ * 2)
      .runFold(0)((acc, x) => acc + x)(mat)
  }

  private def example2(): Unit = {
    val system: ActorSystem = ActorSystem()
    implicit val mat: ActorMaterializer = ActorMaterializer()(system)

    // source
    val source: Source[Int, NotUsed] = Source(1 to 10)

    // flow
    val flow: Flow[Int, Int, NotUsed] = Flow.fromFunction(_ * 2)

    // sink
    val sink: Sink[Int, Future[Int]] =
      Sink.fold(0)((acc: Int, x: Int) => acc + x)

//    val sink2: Sink[Nothing, Future[Int]] =
//      Sink.fold(0)((acc, x) => acc + x)

//    val sink3: Sink[Int, Future[Int]] =
//      Sink.fold(0)((acc, x) => acc + x)


    // composition
    val result: Future[Int] = source.via(flow).runWith(sink)

    // mix source and flow
    val source2: Source[Int, NotUsed] = source.via(flow)

    // mix flow and sink
    val sinkAdapter: Sink[Int, NotUsed] = flow.to(sink)
    val result3: NotUsed = source.runWith(sinkAdapter)
    val result4: NotUsed = source.to(sinkAdapter).run()
  }
}
