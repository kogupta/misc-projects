package kvstore

import java.util.concurrent.atomic.AtomicLong

import akka.actor.SupervisorStrategy.Resume
import akka.actor.{Actor, ActorLogging, ActorRef, AllForOneStrategy, Props, SupervisorStrategy}
import akka.pattern.{ask, pipe}
import akka.util.Timeout
import kvstore.Arbiter._
import kvstore.Persistence.{Persist, Persisted}
import kvstore.Replica.{OperationAck, OperationFailed}
import kvstore.Replicator.{Replicate, SnapshotAck}
import kvstore.RetryPersistence.{AckFailure, PersistenceFailed, PersistenceTimeout}

import scala.concurrent.Future
import scala.concurrent.duration._

object Replica {
  sealed trait Operation {
    def key: String
    def id: Long
  }
  case class Insert(key: String, value: String, id: Long) extends Operation
  case class Remove(key: String, id: Long) extends Operation
  case class Get(key: String, id: Long) extends Operation

  sealed trait OperationReply
  case class OperationAck(id: Long) extends OperationReply
  case class OperationFailed(id: Long) extends OperationReply
  case class GetResult(key: String, valueOption: Option[String], id: Long) extends OperationReply

  def props(arbiter: ActorRef, persistenceProps: Props): Props = Props(new Replica(arbiter, persistenceProps))

  private val replMsgId = new AtomicLong(0)
  private def nextReplMsgId(): Long = replMsgId.decrementAndGet()
}

class Replica(val arbiter: ActorRef, persistenceProps: Props) extends Actor with ActorLogging {
  import Persistence._
  import Replica._
  import Replicator._

  /*
   * The contents of this actor is just a suggestion, you can implement it in any way you like.
   */

  var kv = Map.empty[String, String]
  // a map from secondary replicas to replicators
  var secondaries = Map.empty[ActorRef, ActorRef]
  // the current set of replicators
  var replicators = Set.empty[ActorRef]

  private val persister: ActorRef = context.actorOf(persistenceProps)

  override def supervisorStrategy: SupervisorStrategy = AllForOneStrategy(withinTimeRange = 1.second) {
    case _: PersistenceException => Resume
  }

  var expectedSeq: Long = 0

  override def preStart(): Unit = {
    arbiter ! Join
  }

  def receive: Receive = {
    case JoinedPrimary   => context.become(leader)
    case JoinedSecondary => context.become(replica)
  }

  val leader: Receive = {
    case Replicas(replicas) =>
      val existing = secondaries.keySet
      val others = replicas.tail // first element is leader
      val removed = existing.diff(others)
      removed.foreach { replica: ActorRef =>
        val replicator = secondaries(replica)
        context.stop(replicator)
        replicators = replicators - replicator
        secondaries = secondaries - replica
      }
      val added = others.diff(existing)
      added.foreach{ replica: ActorRef =>
        val replicator = context.actorOf(Replicator.props(replica))
        secondaries = secondaries.updated(replica, replicator)
        replicators = replicators + replicator
        kv.foreach{case (k, v) =>
          replicator ! Replicate(k, Some(v), nextReplMsgId())
        }
      }

    case Insert(key, value, id) =>
      kv = kv updated(key, value)
      val retryActor = context.actorOf(RetryPersistence.props2(
        sender(),
        persister,
        Persist(key, Some(value), id),
        replicators))
      retryActor ! PersistenceTimeout

    case Remove(key, id) =>
      kv = kv - key
      val retryActor = context.actorOf(RetryPersistence.props2(
        sender(),
        persister,
        Persist(key, None, id),
        replicators))
      retryActor ! PersistenceTimeout

    case Get(key, id) =>
      sender() ! GetResult(key, kv.get(key), id)
  }

  val replica: Receive = {
    case Get(key, id) =>
      sender() ! GetResult(key, kv.get(key), id)

    case Snapshot(key, _, seqNum)  if seqNum < expectedSeq =>
      expectedSeq = Math.max(expectedSeq, seqNum + 1)
      sender() ! SnapshotAck(key, seqNum)

    case Snapshot(key, maybeValue, seqNum)  if seqNum == expectedSeq =>
      expectedSeq = Math.max(expectedSeq, seqNum + 1)
      maybeValue match {
        case Some(x) => kv = kv.updated(key, x)
        case None => kv = kv - key
      }
      val persistMsg = Persist(key, maybeValue, seqNum)
      val retryActor =
        context.actorOf(RetryPersistence.props(sender(), persister, persistMsg))
      retryActor ! PersistenceTimeout

    case Snapshot(_, _, seqNum)  if seqNum > expectedSeq =>
  }
}

class RetryPersistence(src: ActorRef, persister: ActorRef, msg: Persist)
  extends Actor with ActorLogging {
  import context.dispatcher
  context.system.scheduler.schedule(0.milliseconds, 100.milliseconds, self, PersistenceTimeout)
  context.system.scheduler.scheduleOnce(1.second, self, PersistenceFailed)

  override def receive: Receive = {
    case Persisted(key, id) =>
      src ! SnapshotAck(key, id)
      context.stop(self)

    case PersistenceTimeout =>
      persister ! msg

    case PersistenceFailed =>
      src ! OperationFailed(msg.id)
      context.stop(self)
  }

  }

class PrimaryRetryPersistence(src: ActorRef, persister: ActorRef, msg: Persist, replicators: Set[ActorRef])
  extends Actor with ActorLogging {

  import context.dispatcher
  context.system.scheduler.schedule(0.milliseconds, 100.milliseconds, self, PersistenceTimeout)
  context.system.scheduler.scheduleOnce(1.second, self, AckFailure)

  override def receive: Receive = {
    case PersistenceTimeout =>
      persister ! msg

    case Persisted(_, id) =>
      if (replicators.isEmpty) {
        src ! OperationAck(id)
      } else {
        implicit val timeout: Timeout = Timeout(1.seconds)
        val futures = replicators map { r: ActorRef =>
          r ? Replicate(msg.key, msg.valueOption, id)
        }

        Future.sequence(futures)
          .map(_ => OperationAck(id))
          .recover { case _ => OperationFailed(id) }
          .pipeTo(src)
      }
      context.stop(self)

    case AckFailure =>
      src ! OperationFailed(msg.id)
      context.stop(self)
  }
}

object RetryPersistence {
  case object PersistenceTimeout
  case object PersistenceFailed
  case object AckFailure

  def props(src: ActorRef, persister: ActorRef, msg: Persist): Props = {
    Props(new RetryPersistence(src, persister, msg))
  }

  def props2(src: ActorRef, persister: ActorRef, msg: Persist, replicators: Set[ActorRef]): Props = {
    Props(new PrimaryRetryPersistence(src, persister, msg, replicators))
  }
}