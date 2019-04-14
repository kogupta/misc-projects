package org.kogu.sedgewick_coursera.trie;

import java.util.stream.IntStream;

import static java.lang.System.out;
import static java.util.stream.Collectors.joining;

public interface Alphabet {
  boolean contains(char c);
  int radix();
  default int ceilLogRadix() {
    int lgR = 0;
    for (int t = radix() - 1; t >= 1; t /= 2)
      lgR++;

    return lgR;

  }

  int indexOf(char c);
  default int[] indicesOf(String s) {
    char[] chars = s.toCharArray();
    int[] result = new int[chars.length];
    for (int i = 0, len = chars.length; i < len; i++) {
      char c = chars[i];
      result[i] = indexOf(c);
    }

    return result;
  }

  char charAt(int index);
  default String charsAt(int[] indices) {
    char[] chars = new char[indices.length];
    for (int i = 0, len = indices.length; i < len; i++) {
      int index = indices[i];
      chars[i] = charAt(index);
    }

    return new String(chars);
  }

  private static void validateDuplicates(String alpha) {
    // check that alphabet contains no duplicate chars
    boolean[] unicode = new boolean[Character.MAX_VALUE];
    for (int i = 0; i < alpha.length(); i++) {
      char c = alpha.charAt(i);
      if (unicode[c])
        throw new IllegalArgumentException("Illegal alphabet: repeated character = '" + c + "'");
      unicode[c] = true;
    }
  }

  static Alphabet lowerCaseAlphabets() { return new LowerCaseAlphabet();}

  static Alphabet upperCaseAlphabets() { return new UpperCaseAlphabet();}

  static void main(String[] args) {
    Alphabet lc = lowerCaseAlphabets();
    String s = IntStream.range(0, lc.radix())
        .mapToObj(i -> String.valueOf(lc.charAt(i)))
        .limit(30)
        .collect(joining(" "));
    out.println(s);
  }
}
