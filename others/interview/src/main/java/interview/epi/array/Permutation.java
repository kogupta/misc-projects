package interview.epi.array;

import java.util.Arrays;
import java.util.function.BiFunction;

import static interview.epi.array.SortFunctions.swap;

public final class Permutation {
  public static void main(String[] args) {
    assertionStatus();
    test(Permutation::applyPermutation);
    test(Permutation::applyPermutation2);

    test2(Permutation::applyPermutation);
    test2(Permutation::applyPermutation2);
  }

  private static void test(BiFunction<int[], String[], String[]> fn) {
    // P[i] represents location of element at i
    int[] permutation = {2, 0, 1, 3};
    String[] input = {"a", "b", "c", "d"};
    String[] expected = {"b", "c", "a", "d"};

    String[] obtained = fn.apply(permutation, input);
//    System.out.println(Arrays.toString(obtained));
    assert Arrays.equals(expected, obtained);
  }

  private static void test2(BiFunction<int[], String[], String[]> fn) {
    // P[i] represents location of element at i
    int[] permutation = {3,  0,  4,  1,  2};
    String[] input = {"a", "b", "c", "d", "e"};
    String[] expected = {"b", "d", "e", "a", "c"};

    String[] obtained = fn.apply(permutation, input);
//    System.out.println(Arrays.toString(obtained));
    assert Arrays.equals(expected, obtained);
  }

  private static String[] applyPermutation(int[] permutation, String[] input) {
    assert permutation.length == input.length;

    int len = permutation.length;
    String[] result = new String[len];
    for (int i = 0; i < len; i++) {
      int pos = permutation[i];
      String t = input[i];
      result[pos] = t;
//      System.out.println("input: " + Arrays.toString(input));
//      System.out.println("input: " + Arrays.toString(result));
    }

    return result;
  }

  private static String[] applyPermutation2(int[] permutation, String[] input) {
    assert permutation.length == input.length;

    int len = permutation.length;
    for (int i = 0; i < len; i++) {
      int next = i;
      while (permutation[next] >= 0) {
//        System.out.println("i: " + i + ", next: " + next);
//        System.out.print("perm: " + Arrays.toString(permutation));
//        System.out.println("\t\tterms: " + Arrays.toString(input));

        int destn = permutation[next];
        swap(input, i, destn);
        permutation[next] = destn - len;
        next = destn;
      }
    }

    for (int i = 0; i < len; i++) {
      permutation[i] = permutation[i] < 0 ? permutation[i] + len : permutation[i];
    }

//    System.out.println("final permutation: " + Arrays.toString(permutation));

    return input;
  }

  private static void assertionStatus() {
    String status = Permutation.class.desiredAssertionStatus() ? "enabled" : "disabled";
    System.out.println("Assertion: " + status);
    System.out.println("====================");
  }

}
