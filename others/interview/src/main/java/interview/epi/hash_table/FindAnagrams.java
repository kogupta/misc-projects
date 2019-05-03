package interview.epi.hash_table;

import org.jetbrains.annotations.NotNull;

import java.util.*;

// given a dictionary, find list of words which are anagrams
public final class FindAnagrams {
  public static void main(String[] args) {

  }

  private static Map<String, List<String>> buildAnagramIndex(Iterable<String> dictionary) {
    Map<String, List<String>> result = new HashMap<>();
    for (String word : dictionary) {
      char[] chars = word.toCharArray();
      Arrays.sort(chars);
      String anagram = new String(chars);
      result.compute(anagram, (s, xs) -> createOrAppend(s, xs));
    }

    return result;
  }

  @NotNull
  private static List<String> createOrAppend(String s, List<String> xs) {
    if (xs == null) {
      List<String> p = new ArrayList<>();
      p.add(s);
      return p;
    } else {
      xs.add(s);
      return xs;
    }
  }
}
