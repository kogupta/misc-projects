package interview.epi.strings;

import static interview.epi.array.SortFunctions.assertionStatus;

public final class RLE {
  public static void main(String[] args) {
    assertionStatus();

    assert encode("eeeffffee").equals("3e4f2e");
    assert decode("3e4f2e").equals("eeeffffee");

    String[] ss = {"aaaabcccaa", "eeeffffee"};
    for (String s : ss) {
      String encoded = encode(s);
      System.out.println("Encoding: " + s + " => " + encoded);
      String decoded = decode(encoded);
      System.out.println("Decoding: " + encoded + " => " + decoded);
      System.out.println();
      assert decoded.equals(s);
    }
  }

  private static String decode(String s) {
    int times = 0;
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (Character.isDigit(c)) {
        times = times * 10 + c - '0';
      } else {  // c is char -> repeat
        sb.append(String.valueOf(c).repeat(times));
        times = 0;
      }
    }

    return sb.toString();
  }

  private static String encode(String s) {
    StringBuilder acc = new StringBuilder();
    int n = 1;

    for (int i = 1; i <= s.length(); i++) {
      if (i == s.length() || s.charAt(i) != s.charAt(i - 1)) {
        acc.append(n).append(s.charAt(i - 1));
        n = 1;
      } else {
        // curr char == prev char
        n++;
      }
    }

    return acc.toString();
  }
}
