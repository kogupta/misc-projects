package interview.epi.strings;

import java.util.Arrays;

import static interview.epi.array.SortFunctions.assertionStatus;

public final class ReverseWords {
  public static void main(String[] args) {
    assertionStatus();

    reverseWords("ram is costly");
    reverseWords("Alice likes Bob");
  }

  private static void reverseWords(String sentence) {
    System.out.println("to reverse: " + sentence);

    char[] chars = sentence.toCharArray();
    int length = chars.length;

    reverse(chars, 0, length - 1);
    System.out.println("reversed: " + Arrays.toString(chars));

    for (int i = 0; i < length; ) {
      int j = find(chars, i);
      reverse(chars, i, j - 1);
      i = j + 1;
    }

    System.out.println(chars);
    System.out.println();
  }

  private static void reverse(char[] chars, int from, int to) {
    for (int i = from, j = to; i < j; i++, j--) {
      swap(chars, i, j);
    }
  }

  // find ' ' character starting from `start`.
  // if not found, ie, array ends, return array length
  private static int find(char[] chars, int start) {
    int length = chars.length;
    for (int i = start; i < length; i++) {
      char c = chars[i];
      if (c == ' ') return i;
    }

    return length;
  }

  private static void swap(char[] chars, int i, int j) {
    char t = chars[i];
    chars[i] = chars[j];
    chars[j] = t;
  }
}
