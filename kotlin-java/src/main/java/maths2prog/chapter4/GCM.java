package maths2prog.chapter4;

import java.util.Arrays;

public final class GCM {
  public static void main(String[] args) {
    int a = 196, b = 42;
    System.out.println(gcm0(a, b)); line();
    System.out.println(_gcm0(a, b, 0)); line();
    System.out.println(gcm1(a, b)); line();
  }

  private static void line() {
    System.out.println("-----------------------------");
  }

  private static int gcm0(int a, int b) {
    if (a == b) { return a;}
    return a > b ? gcm0(b, a - b) : gcm0(a, b - a);
  }

  private static int _gcm0(int a, int b, int depth) {
    char[] chars = new char[depth];
    Arrays.fill(chars, ' ');
    String s = new String(chars);
    System.out.printf("%sgcm(%d, %d)%n", s, a, b);
    if (a == b) { return a;}
    return a > b ? _gcm0(b, a - b, depth + 1) : _gcm0(a, b - a, depth + 1);
  }

  private static int gcm1(int a, int b) {
    while (a != b) {
      a = remainder(a, b);
      // swap
      a = a ^ b; b = a ^ b; a = a ^ b;
    }

    return a;
  }

  private static int remainder(int a, int b) {
    while (b < a) a = a - b;
    return a;
  }
}
