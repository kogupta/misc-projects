package interview.epi.stack_queue

fun main(args: Array<String>) {
  println("hello!")

  val a = IntNode(1)
  val b = IntNode(2)
  val c = IntNode(3)
  val d = IntNode(4)
  a.jump = c
  c.jump = b
  b.jump = d
  d.jump = d

  a.next = b
  b.next = c
  c.next = d

  val path = path(a)
  val s = path.joinToString(
      separator = "->",
      transform = { it.data.toString() }
  )
  println(s)
}

private class IntNode internal constructor(internal val data: Int) {
  internal var order: Int = -1
  internal var jump: IntNode? = null
  internal var next: IntNode? = null
}

private fun path(start: IntNode): List<IntNode> {
  fun helper(node: IntNode?, acc: Pair<List<IntNode>, Int>): Pair<List<IntNode>, Int> {
    return if (node == null || node.order != -1) acc
    else {
      val (path, order) = acc
      node.order = order
      val newAcc = helper(node.jump, Pair(path.plus(node), order + 1))
      helper(node.next, newAcc)
    }
  }

  val (path, _) = helper(start, Pair(listOf(), 0))

  return path
}