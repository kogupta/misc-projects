package interview.epi.hash_table;

import java.util.HashMap;
import java.util.Map;

// Lets get into crime: write an anonymous letter
// using cut outs from magazine!
public final class AnonymousLetter {
  public static void main(String[] args) {

  }

  private static boolean buildFromMagazine(String magazine, String letter) {
    Map<Character, Integer> hist = histogram(letter);
    for (int i = 0; i < magazine.length() && !hist.isEmpty(); i++) {
      char c = magazine.charAt(i);
      hist.computeIfPresent(c, (ignore, n) -> n - 1);
      if (hist.containsKey(c)) {
        hist.put(c, hist.get(c) - 1);
        if (hist.get(c) == 0) {
          hist.remove(c);
        }
      }
    }

    return hist.isEmpty();
  }

  private static Map<Character, Integer> histogram(String letter) {
    Map<Character, Integer> hist = new HashMap<>();
    for (int i = 0; i < letter.length(); i++) {
      char c = letter.charAt(i);
      hist.merge(c, 1, Integer::sum);
    }

    return hist;
  }
}
