package org.kogupta.scala_refresh

object App {
  def foo(x: Array[String]): String = x.foldLeft("")((a, b) => a + b)

  def main(args: Array[String]) {
    println("Hello World!")
    println(s"concat arguments = ${foo(args)}")
  }

}
