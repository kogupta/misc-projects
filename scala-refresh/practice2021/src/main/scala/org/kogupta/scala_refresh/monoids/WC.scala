package org.kogupta.scala_refresh.monoids

sealed trait WC

case class Stub(chars: String) extends WC

case class Part(lStub: String, words: Int, rStub: String) extends WC

object WC {
  val wcMonoid: Monoid[WC] = new Monoid[WC] {
    override def op(x: WC, y: WC): WC = (x, y) match {
      case (Stub(a), Stub(b)) => Stub(a + b)
      case (Stub(a), Part(l, n, r)) => Part(a + l, n, r)
      case (Part(l, n, r), Stub(a)) => Part(l, n, r + a)
      case (Part(la, na, ra), Part(lb, nb, rb)) =>
        val mid = ra + lb
        val n = if (mid.isEmpty) 0 else 1
        Part(la, na + n + nb, rb)
    }

    override def zero: WC = Stub("")
  }

  // implement a function that counts words in a String
  // by recursively splitting it into substrings and
  // counting the words in those substrings
  def count(s: String): Int = {
    def char2Wc(c: Char): WC =
      if (c.isWhitespace) Part("", 0, "") else Stub(c.toString)

    // `unstub(s)` is 0 if `s` is empty, otherwise 1.
    def unstub(s: String) = s.length min 1

    Monoid.foldMapV(s.toIndexedSeq, wcMonoid)(char2Wc) match {
      case Stub(chars) => unstub(chars)
      case Part(l, n, r) => unstub(l) + n + unstub(r)
    }
  }
}