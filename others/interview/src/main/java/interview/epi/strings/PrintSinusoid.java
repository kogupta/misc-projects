package interview.epi.strings;

// input: Hello.World
//    e       .       l
//  H   l   o   W   r   d
//        l       o
//
// notes:
//  - 3 rows; numbers are repeated as: [4k, 4k + 1, 4k + 2, 4k + 3, ...]
//  - top row indices:    1       5
//  - mid row indices:  0   2   4   6  ....
//  - bot row indices:        3       7
public final class PrintSinusoid {
  public static void main(String[] args) {
    printSine("Hello World");
    System.out.println();
    printSine("This_is_a_long_STRING");
  }

  private static void printSine(String s) {
    Triple triple = partition(s);
    System.out.println(triple.a);
    System.out.println(triple.b);
    System.out.println(triple.c);
  }

  private static Triple partition(String s) {
    Triple triple = new Triple();
    for (int i = 0; i < s.length(); i++) {
      if (i % 2 == 0) { // mid row
        triple.a.append(' ').append(' ');
        triple.b.append(s.charAt(i)).append(' ');
        triple.c.append(' ').append(' ');
      } else if (i % 4 == 1) {  // top row
        triple.a.append(s.charAt(i)).append(' ');
        triple.b.append(' ').append(' ');
        triple.c.append(' ').append(' ');
      } else {  // bot row
        triple.a.append(' ').append(' ');
        triple.b.append(' ').append(' ');
        triple.c.append(s.charAt(i)).append(' ');
      }
    }

    return triple;
  }

  private static final class Triple {
    final StringBuilder a, b, c;

    private Triple() {
      a = new StringBuilder();
      b = new StringBuilder();
      c = new StringBuilder();
    }
  }
}
