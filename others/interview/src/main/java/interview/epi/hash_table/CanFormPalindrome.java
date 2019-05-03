package interview.epi.hash_table;

import java.util.HashMap;
import java.util.Map;

public final class CanFormPalindrome {
  public static void main(String[] args) {

  }

  private static boolean canFormPalindrome(String s) {
    Map<Character, Integer> freqs = charFreqs(s);

    long count = freqs.values().stream()
        .mapToInt(n -> n)
        .filter(n -> isOdd(n))
        .count();
    return count == 0 || count == 1;
  }

  private static boolean isOdd(int n) {
    return n % 2 == 1;
  }

  private static Map<Character, Integer> charFreqs(String s) {
    Map<Character, Integer> freqs = new HashMap<>();
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      freqs.merge(c, 1, Integer::sum);
    }

    return freqs;
  }
}
