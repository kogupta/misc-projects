package interview.epi.strings;

import static interview.epi.array.SortFunctions.assertionStatus;

public final class Convert {
  public static void main(String[] args) {
    assertionStatus();

    int n = 121234;
    String asString = "121234";
    String negString = "-121234";

    assert toInt(asString) == n;
    assert toInt(negString) == -n;

    assert toString(n).equals(asString);
    assert toString(-n).equals(negString);

    testConvert(n, asString);

    testConvert("11111111111", "102", 1, 3);
    testConvert("11111111111", "23", 1, 4);
    testConvert("102", "23", 3, 4);
    testConvert("615", "1a7", 7, 13);
  }

  private static void testConvert(String src, String target,
                                  int fromBase, int toBase) {
    System.out.printf("Checking for: %s, base:%d => %d%n",
        src, fromBase, toBase);

    String got = convert(src, fromBase, toBase);
    assert got.equals(target) : "Expected: " + target + ", got: " + got;

    got = convert(target, toBase, fromBase);
    assert got.equals(src) : "Expected: " + src + ", got: " + got;

    System.out.println();
  }

  private static void testConvert(int n, String expected) {
    String binary = Integer.toBinaryString(n);
    String hex = Integer.toHexString(n);

    assert convert(binary, 2, 10).equals(expected);
    assert convert(hex, 16, 10).equals(expected);
    assert convert(binary, 2, 16).equals(hex);
    assert convert(hex, 16, 2).equals(binary);

    System.out.println();
  }

  // base 10 conversion
  private static int toInt(String s) {
    int n = 0;

    // if negative number, start from next index
    int startIdx = s.charAt(0) == '-' ? 1 : 0;
    for (; startIdx < s.length(); startIdx++) {
      char c = s.charAt(startIdx);
      n = 10 * n + (c - '0');
    }

    return s.charAt(0) == '-' ? -n : n;
  }

  // base 10 conversion
  private static String toString(final int n) {
    boolean isNegative = n < 0;

    int x = Math.abs(n);

    StringBuilder sb = new StringBuilder();
    do {
      int r = x % 10;
      sb.append(r);
      x /= 10;
    } while (x != 0);

    if (isNegative) sb.append('-');

    return sb.reverse().toString();
  }

  // A -> 10, B -> 11, ....
  private static String convert(String number, int fromBase, int toBase) {
    assert fromBase > 0 && fromBase <= 'z' - 'a' + 10;
    assert toBase > 0 && toBase <= 'z' - 'a' + 10;

    boolean isNegative = number.charAt(0) == '-';

    int base10 = 0;
    int startIdx = isNegative ? 1 : 0;
    for (; startIdx < number.length(); startIdx++) {
      char c = number.charAt(startIdx);
      int n = Character.isDigit(c) ? c - '0' : c - 'a' + 10;
      base10 = fromBase * base10 + n;
    }

    System.out.printf("[%s, base:%d] => [%d, base:10]%n", number, fromBase, base10);

    String s = toString(base10, toBase);
    System.out.printf("[%d, base:10] => [%s, base:%s]%n", base10, s, toBase);

    return isNegative ? "-" + s : s;
  }

  private static String toString(int base10, int toBase) {
    if (toBase == 10) return Integer.toString(base10);
    if (toBase == 1) return Integer.toString(1).repeat(base10);

    StringBuilder sb = new StringBuilder();

    do {
      int r = base10 % toBase;
      char c = (char) (r >= 10 ? 'a' - 10 + r : '0' + r);
      sb.append(c);
      base10 /= toBase;
    } while (base10 != 0);

    return sb.reverse().toString();
  }

}
