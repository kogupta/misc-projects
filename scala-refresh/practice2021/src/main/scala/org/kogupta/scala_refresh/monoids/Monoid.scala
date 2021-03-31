package org.kogupta.scala_refresh.monoids

trait Monoid[A] {
  // op(op(x, y), z) == op(x, op(y, z))
  def op(a1: A, a2: A): A

  // op(zero, a) == op(a, zero) == a
  def zero: A
}

object Monoid {
  def main(args: Array[String]): Unit = {
    val double: Int => Int = _ * 2
    val triple: Int => Int = _ * 3
    val add1: Int => Int = _ + 1

    {
      val f1 = endoMonoid.op(double, endoMonoid.op(triple, add1))
      val f2 = endoMonoid.op(endoMonoid.op(double, triple), add1)
      (1 to 10).foreach(n => assert(f1(n) == f2(n)))
    }

    {
      val f1 = endoMonoid.op(add1, endoMonoid.op(triple, add1))
      val f2 = endoMonoid.op(endoMonoid.op(add1, triple), add1)
      (1 to 10).foreach(n => assert(f1(n) == f2(n)))
    }
  }

  val stringMonoid: Monoid[String] = new Monoid[String] {
    override def op(a1: String, a2: String): String = a1 + a2

    override def zero: String = ""
  }

  def listMonoid[A]: Monoid[List[A]] = new Monoid[List[A]] {
    override def op(a1: List[A], a2: List[A]): List[A] = a1 ++ a2

    override def zero: List[A] = Nil
  }

  val intAddition: Monoid[Int] = new Monoid[Int] {
    override def op(a1: Int, a2: Int): Int = a1 + a2

    override def zero: Int = 0
  }

  val intMultiplication: Monoid[Int] = new Monoid[Int] {
    override def op(a1: Int, a2: Int): Int = a1 * a2

    override def zero: Int = 1
  }

  val booleanOr: Monoid[Boolean] = new Monoid[Boolean] {
    override def op(a1: Boolean, a2: Boolean): Boolean = a1 || a2

    override def zero: Boolean = false
  }

  val booleanAnd: Monoid[Boolean] = new Monoid[Boolean] {
    override def op(a1: Boolean, a2: Boolean): Boolean = a1 && a2

    override def zero: Boolean = true
  }

  // We can get the dual of any monoid just by flipping the `op`.
  def dual[A](m: Monoid[A]): Monoid[A] = new Monoid[A] {
    override def op(x: A, y: A): A = m.op(y, x)

    override val zero: A = m.zero
  }

  def optionMonoid[A]: Monoid[Option[A]] = new Monoid[Option[A]] {
    override def op(x: Option[A], y: Option[A]): Option[A] = x orElse y

    override def zero: Option[A] = None
  }

  def endoMonoid[A]: Monoid[A => A] = new Monoid[A => A] {
    override def op(f: A => A, g: A => A): A => A = f compose g

    override def zero: A => A = identity
  }

  def concatenate[A](as: List[A], m: Monoid[A]): A = as.foldLeft(m.zero)(m.op)

  def foldMap[A, B](as: List[A], m: Monoid[B])(f: A => B): B = concatenate(as.map(f), m)

  def foldMap2[A, B](as: List[A], m: Monoid[B])(f: A => B): B =
    as.foldLeft(m.zero)((b: B, a: A) => m.op(b, f(a)))

  // fold left in terms of foldMap
  def foldLeft2[A, B](as: List[A])(z: B)(f: (B, A) => B): B =
    foldMap(as, dual(endoMonoid[B]))(a => b => f(b, a))(z)

  def foldRight2[A, B](as: List[A])(z: B)(f: (A, B) => B): B =
    foldMap(as, endoMonoid[B])(f.curried)(z)

  def foldMapV[A, B](as: IndexedSeq[A], m: Monoid[B])(f: A => B): B = {
    if (as.isEmpty)
      m.zero
    else if (as.length == 1)
      f(as(0))
    else {
      val (left, right) = as.splitAt(as.length / 2)
      m.op(foldMapV(left, m)(f), foldMapV(right, m)(f))
    }
  }

  // This implementation detects only ascending order,
  // but you can write a monoid that detects both ascending and descending
  // order if you like.
  def ordered(ints: IndexedSeq[Int]): Boolean = {
    // Our monoid tracks the minimum and maximum element seen so far
    // as well as whether the elements are so far ordered.
    val mon = new Monoid[Option[(Int, Int, Boolean)]] {
      override def op(o1: Option[(Int, Int, Boolean)],
                      o2: Option[(Int, Int, Boolean)]): Option[(Int, Int, Boolean)] =
        (o1, o2) match {
          // The ranges should not overlap if the sequence is ordered.
          case (Some((x1, y1, p)), Some((x2, y2, q))) =>
            Some((x1 min x2, y1 max y2, p && q && y1 <= x2))
          case (x, None) => x
          case (None, x) => x
        }

      override val zero: Option[(Int, Int, Boolean)] = None
    }
    // The empty sequence is ordered, and each element by itself is ordered.
    foldMapV(ints, mon)(i => Some((i, i, true))).forall(_._3)
  }
}