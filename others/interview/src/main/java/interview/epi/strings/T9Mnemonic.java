package interview.epi.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static interview.epi.array.SortFunctions.assertionStatus;

// phone number => List<Mnemonic>
// Mnemonic => phone number
public final class T9Mnemonic {
  private static final String[] mapping = {
      "0", "1", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ"
  };

  private static final int[] reverseIndex = reverseIndex(mapping);

  public static void main(String[] args) {
    assertionStatus();

    String number = "2276696";
    assert toNumber("ACRONYM").equals(number);
    assert toNumber("ABPOMZN").equals(number);

    List<String> mnemonics = mnemonicsOf(number);
    assert mnemonics.contains("ACRONYM");
    assert mnemonics.contains("ABPOMZN");
  }

  private static String toNumber(String mnemonic) {
    StringBuilder sb = new StringBuilder(mnemonic.length());
    for (int i = 0; i < mnemonic.length(); i++) {
      char c = mnemonic.charAt(i);
      sb.append(toDigit(c));
    }

    return sb.toString();
  }

  private static List<String> mnemonicsOf(String number) {
    char[] mnemonic = new char[number.length()];
    List<String> mnemonics = new ArrayList<>();
    helper(number, mnemonic, 0, mnemonics);

    return mnemonics;
  }

  private static void helper(String number, char[] partialMnemonic, int index, List<String> acc) {
    if (index == number.length()) {
      acc.add(new String(partialMnemonic));
    } else {
      int digit = number.charAt(index) - '0';
      String chars = mapping[digit];
      for (int i = 0; i < chars.length(); i++) {
        char c = chars.charAt(i);
        partialMnemonic[index] = c;
        helper(number, partialMnemonic, index + 1, acc);
      }
    }
  }

  private static int toDigit(char c) {
    assert c == '0' || c == '1' || ('A' <= c && c <= 'Z');

    if (c == '0' || c == '1') return c - '0';
    int idx = c - 'A' + 2;
    return reverseIndex[idx];
  }

  // build the reverse index of: char => digit
  private static int[] reverseIndex(String[] mapping) {
    assert mapping != null && mapping.length == 10;

    int len = 'Z' - 'A' + 3; // 0 and 1
    int[] reverseIndex = new int[len];

    reverseIndex[0] = 0;
    reverseIndex[1] = 1;

    // mapping.length == 10
    // i <= 9
    for (int i = 2; i < mapping.length; i++) {
      String s = mapping[i];
      for (int j = 0; j < s.length(); j++) {
        char c = s.charAt(j);
        int idx = c - 'A' + 2;
        reverseIndex[idx] = i;
      }
    }

    System.out.println("reverse index: " + Arrays.toString(reverseIndex));
    return reverseIndex;
  }
}
