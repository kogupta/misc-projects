package kvstore

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

import scala.concurrent.duration._

object Replicator {
  case class Replicate(key: String, valueOption: Option[String], id: Long)
  case class Replicated(key: String, id: Long)

  case class Snapshot(key: String, valueOption: Option[String], seq: Long)
  case class SnapshotAck(key: String, seq: Long)

  case object Retry

  def props(replica: ActorRef): Props = Props(new Replicator(replica))
}

class Replicator(val replica: ActorRef) extends Actor with ActorLogging {
  import Replicator._
  import context.dispatcher

  // map from sequence number to pair of sender and request
  var acks = Map.empty[Long, (ActorRef, Replicate)]
  // a sequence of not-yet-sent snapshots (you can disregard this if not implementing batching)
  var pending = Vector.empty[Snapshot]
  implicit val snapOrder: Ordering[Snapshot] = Ordering.by(s => (s.key, s.seq))

  private var _seqCounter = 0L
  private def nextSeq(): Long = {
    val ret = _seqCounter
    _seqCounter += 1
    ret
  }

  context.system.scheduler.schedule(0.milliseconds, 100.milliseconds, self, Retry)

  def receive: Receive = {
    case r@Replicate(key, maybeValue, id) =>
      val seq = nextSeq()
      acks = acks.updated(seq, (sender(), r))
      val snapshot = Snapshot(key, maybeValue, seq)
      pending = batch(snapshot)
      replica ! snapshot

    case SnapshotAck(key, seqNum) =>
      acks.get(seqNum) match {
        case Some((ref, msg)) =>
          acks = acks - seqNum
          pending = pending.filterNot(s => s.key == key && s.seq == seqNum)
          ref ! Replicated(key, msg.id)
        case None =>
      }

    case Retry =>
      pending.foreach(replica ! _)
  }

  override def postStop(): Unit = {
    acks.foreach{
      case (_, (client, replicate)) =>
        client ! Replicated(replicate.key, replicate.id)
    }
  }

  private def batch(snap: Snapshot): Vector[Snapshot] = {
    val idx = pending.indexWhere(snap.key == _.key)
    if (idx != -1)
      pending = pending.updated(idx, snap)
    else
      pending = pending :+ snap

    pending.sorted
  }
}