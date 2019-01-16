package miscTests;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.toBinaryString;

public class Misc {
  public static boolean mux(boolean sel, boolean a, boolean b) {
    return (sel && b) || (!sel && a);
  }

  public static boolean addMuxOr(boolean sel, boolean a, boolean b) {
    return (sel && (a || b)) || (!sel && a && b);
  }

  public static Pair demux(boolean in, boolean sel) {
    if (sel) {
      return Pair.of(false, in);
    } else {
      return Pair.of(in, false);
    }
  }

  private static boolean blah(boolean f, boolean x, boolean y) {
    return (f && (x || y)) || (!f && x && y);
  }

  static final class Pair {
    final boolean a;
    final boolean b;

    Pair(boolean a, boolean b) {
      this.a = a;
      this.b = b;
    }

    static Pair of(boolean a, boolean b) {
      return new Pair(a, b);
    }
  }

  public static void main(String[] args) {
    String s = toBinaryString(99);
    System.out.println(s);

    int n = parseInt("01111101", 2) + parseInt("00110110", 2);
    System.out.println(toBinaryString(n));
  }
}
