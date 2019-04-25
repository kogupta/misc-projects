package org.kogu.misc;

public final class BewareOverflow {
  private BewareOverflow() {}

  public static void main(String[] args) {
    asInts();
    System.out.println("---------------------------");
    asLongs();
  }

  private static void asInts() {
//    int lo = -2147483648;
//    int hi =  2147483647;
    int lo = Integer.MIN_VALUE;
    int hi = Integer.MAX_VALUE;

    // predictable only when both are positive
    System.out.println(lo + (hi - lo) / 2); // nay :(

    long x = (long) lo + (long) hi;
    assert x == -1; // yay!!
  }

  private static void asLongs() {
    long lo = Integer.MIN_VALUE;
    long hi = Integer.MAX_VALUE;

    System.out.println(lo + (hi - lo) / 2);

    assert lo + hi == -1; // yay
  }
}
