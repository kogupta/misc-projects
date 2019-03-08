package kvstore

import java.util.concurrent.atomic.AtomicLong

import akka.actor.{Actor, ActorLogging, ActorRef, AllForOneStrategy, OneForOneStrategy, PoisonPill, Props, ReceiveTimeout, SupervisorStrategy, Terminated}
import kvstore.Arbiter._

import scala.collection.immutable.Queue
import akka.actor.SupervisorStrategy.{Restart, Resume, Stop}

import scala.annotation.tailrec
import akka.pattern.{ask, pipe}

import scala.concurrent.duration._
import akka.util.Timeout
import kvstore.Persistence.{Persist, Persisted}
import kvstore.Replica.OperationAck
import kvstore.Replicator.SnapshotAck
import kvstore.RetryPersistence.PersistenceTimeout

import scala.concurrent.Future

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
  import Replica._
  import Replicator._
  import Persistence._
  import context.dispatcher

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
      kv = kv.updated(key, value)

      implicit val timeout: Timeout = Timeout(1.second)
      persister.ask(Persist(key, Some(value), id))
        .map(_ => OperationAck(id))
        .recover { case _ => OperationFailed(id) }
        .pipeTo(sender())
      replicators.foreach{ref => ref ! Replicate(key, Some(value), id) }


    case Remove(key, id) =>
      kv = kv - key

      implicit val timeout: Timeout = Timeout(1.second)
      persister.ask(Persist(key, None, id))
        .map(_ => OperationAck(id))
        .recover { case _ => OperationFailed(id) }
        .pipeTo(sender())
      replicators.foreach{ref => ref ! Replicate(key, None, id) }


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
        context.actorOf(RetryPersistence.props(sender(), persister, persistMsg, isPrimary = false))
      retryActor ! PersistenceTimeout

    case Snapshot(_, _, seqNum)  if seqNum > expectedSeq =>
  }
}

class RetryPersistence(src: ActorRef, persister: ActorRef, msg: Persist, isPrimary: Boolean)
  extends Actor with ActorLogging {
  import context.dispatcher
  context.system.scheduler.schedule(100.milliseconds, 100.milliseconds, self, PersistenceTimeout)

  override def receive: Receive = if (isPrimary) primaryRetry else secondaryRetry

  private val primaryRetry: Receive = {
    case Persisted(_, id) =>
      src ! OperationAck(id)
      context.stop(self)

    case PersistenceTimeout =>
      persister ! msg
  }

  private val secondaryRetry: Receive = {
    case Persisted(key, id) =>
      src ! SnapshotAck(key, id)
      context.stop(self)

    case PersistenceTimeout =>
      persister ! msg
  }
}

object RetryPersistence {
  case object PersistenceTimeout

  def props(src: ActorRef, persister: ActorRef, msg: Persist, isPrimary: Boolean): Props =
    Props(new RetryPersistence(src, persister, msg, isPrimary))
}