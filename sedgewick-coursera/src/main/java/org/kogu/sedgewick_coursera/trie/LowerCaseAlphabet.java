package org.kogu.sedgewick_coursera.trie;

// a..z
final class LowerCaseAlphabet implements Alphabet {
  private final int radix;
  private final char[] chars;

  LowerCaseAlphabet() {
    radix = 'z' - 'a';
    chars = new char[radix];
    for (int i = 0; i < radix; i++)
      chars[i] = (char) ('a' + i);
  }

  @Override public boolean contains(char c) { return 'a' <= c && c <= 'z';}

  @Override public int radix() { return radix;}

  @Override public int indexOf(char c) { return c -'a';}

  @Override
  public char charAt(int index) {
    assert (0 <= index && index < radix);
    return chars[index];
  }
}

// A..Z
final class UpperCaseAlphabet implements Alphabet {
  private final int radix;
  private final char[] chars;

  UpperCaseAlphabet() {
    radix = 'Z' - 'A';
    chars = new char[radix];
    for (int i = 0; i < radix; i++)
      chars[i] = (char) ('A' + i);
  }

  @Override public boolean contains(char c) { return 'A' <= c && c <= 'Z';}

  @Override public int radix() { return radix;}

  @Override public int indexOf(char c) { return c -'A';}

  @Override
  public char charAt(int index) {
    assert (0 <= index && index < radix);
    return chars[index];
  }
}
