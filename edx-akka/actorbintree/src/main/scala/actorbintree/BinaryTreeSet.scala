/**
 * Copyright (C) 2009-2013 Typesafe Inc. <http://www.typesafe.com>
 */
package actorbintree

import akka.actor._
import akka.event.LoggingReceive

import scala.collection.immutable.Queue

object BinaryTreeSet {

  trait Operation {
    def requester: ActorRef
    def id: Int
    def elem: Int
  }

  trait OperationReply {
    def id: Int
  }

  /** Request with identifier `id` to insert an element `elem` into the tree.
    * The actor at reference `requester` should be notified when this operation
    * is completed.
    */
  case class Insert(requester: ActorRef, id: Int, elem: Int) extends Operation

  /** Request with identifier `id` to check whether an element `elem` is present
    * in the tree. The actor at reference `requester` should be notified when
    * this operation is completed.
    */
  case class Contains(requester: ActorRef, id: Int, elem: Int) extends Operation

  /** Request with identifier `id` to remove the element `elem` from the tree.
    * The actor at reference `requester` should be notified when this operation
    * is completed.
    */
  case class Remove(requester: ActorRef, id: Int, elem: Int) extends Operation

  /** Request to perform garbage collection*/
  case object GC

  /** Holds the answer to the Contains request with identifier `id`.
    * `result` is true if and only if the element is present in the tree.
    */
  case class ContainsResult(id: Int, result: Boolean) extends OperationReply
  
  /** Message to signal successful completion of an insert or remove operation. */
  case class OperationFinished(id: Int) extends OperationReply

}


class BinaryTreeSet extends Actor {
  import BinaryTreeSet._
  import BinaryTreeNode._

  def createRoot: ActorRef = context.actorOf(BinaryTreeNode.props(0, initiallyRemoved = true))

  var root = createRoot

  // optional
  var pendingQueue = Queue.empty[Operation]

  // optional
  def receive = normal

  // optional
  /** Accepts `Operation` and `GC` messages. */
  val normal: Receive = {
    case ops: Operation =>
      root ! ops
    case GC => ???
  }

  // optional
  /** Handles messages while garbage collection is performed.
    * `newRoot` is the root of the new binary tree where we want to copy
    * all non-removed elements into.
    */
  def garbageCollecting(newRoot: ActorRef): Receive = ???

}

object BinaryTreeNode {
  trait Position

  case object Left extends Position
  case object Right extends Position

  case class CopyTo(treeNode: ActorRef)
  case object CopyFinished

  def props(elem: Int, initiallyRemoved: Boolean) = Props(classOf[BinaryTreeNode],  elem, initiallyRemoved)
}

class BinaryTreeNode(val elem: Int, initiallyRemoved: Boolean) extends Actor with ActorLogging {
  import BinaryTreeNode._
  import BinaryTreeSet._

  var subtrees: Map[Position, ActorRef] = Map[Position, ActorRef]()
  var removed: Boolean = initiallyRemoved


  // optional
  def receive = normal

  // optional
  /** Handles `Operation` messages and `CopyTo` requests. */
  val normal: Receive = LoggingReceive {
    case put@Insert(requester: ActorRef, id: Int, x: Int) =>
      log.info(toString() + " - insert: id:" + id + ", elem:" + x)
      if      (x < elem) searchOrUpdate(put, Left)
      else if (x > elem) searchOrUpdate(put, Right)
      else {
        removed = false
        requester ! OperationFinished(id)
      }

    case msg@Contains(requester: ActorRef, id: Int, x: Int) =>
      log.info(toString() + " - contains: id:" + id + ", elem:" + x)
      if      (x == elem) requester ! ContainsResult(id, result = !removed)
      else if (x < elem) contains(msg, Left)
      else               contains(msg, Right)

    case msg@Remove(requester: ActorRef, id: Int, x: Int) =>
      log.info(toString() + " - remove: id:" + id + ", elem:" + x)
      if      (x < elem) maybeRemove(msg, Left)
      else if (x > elem) maybeRemove(msg, Right)
      else {
        removed = true
        requester ! OperationFinished(id)
      }

    case _ => ???
  }

  private def maybeRemove(msg: Remove, position: Position): Unit = {
    subtrees.get(position) match {
      case Some(ref) => ref ! msg
      case None => msg.requester ! OperationFinished(msg.id)
    }
  }

  private def contains(msg: Contains, position: Position): Unit = {
    subtrees.get(position) match {
      case Some(ref) => ref ! msg
      case None => msg.requester ! ContainsResult(msg.id, result = false)
    }
  }

  private def searchOrUpdate(msg: Insert, position: Position): Unit = {
    subtrees.get(position) match {
      case Some(actorRef) => actorRef ! msg
      case None =>
        val ref = context.actorOf(props(msg.elem, initiallyRemoved = false))
        subtrees = subtrees.updated(position, ref)
        msg.requester ! OperationFinished(msg.id)
    }
  }

  // optional
  /** `expected` is the set of ActorRefs whose replies we are waiting for,
    * `insertConfirmed` tracks whether the copy of this node to the new tree has been confirmed.
    */
  def copying(expected: Set[ActorRef], insertConfirmed: Boolean): Receive = ???

  override def toString = s"Node[subtrees:$subtrees, removed:$removed, elem:$elem, initRemoved:$initiallyRemoved]"
}
