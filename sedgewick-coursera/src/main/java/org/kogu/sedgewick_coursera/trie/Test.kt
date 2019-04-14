package org.kogu.sedgewick_coursera.trie

import java.lang.Long

fun main() {
  println("hello")
  val s = Long.toBinaryString(123456789123456789).padStart(64, '0')
  println(s.format("%04x"))
}