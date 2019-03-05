package org.kogu.misc;

public final class Misc {
  private Misc() {}

  public static void main(String[] args) {
//    int lo = -2147483648;
//    int hi =  2147483647;
    int lo = Integer.MIN_VALUE;
    int hi = Integer.MAX_VALUE;
    System.out.println(lo + (hi - lo) / 2); // works only when both are positive
    System.out.println((lo + hi) >>> 1);
    System.out.println((long) lo + (long) hi);
  }
}
