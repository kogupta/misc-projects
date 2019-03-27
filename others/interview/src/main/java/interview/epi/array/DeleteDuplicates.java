package interview.epi.array;

import java.util.Arrays;

public final class DeleteDuplicates {
  public static void main(String[] args) {
    assertionStatus();

    int numUniques = 10;

    int[] xs = new int[100];
    Arrays.setAll(xs, i -> i % numUniques);
    Arrays.sort(xs);

    int lastIndex = deleteDuplicates(xs);
    assert lastIndex == numUniques;

    for (int i = 0; i < lastIndex; i++) {
      System.out.print(xs[i] + " ");
    }

    System.out.println();
  }

  private static int deleteDuplicates(int[] xs) {
    if (xs.length <= 1) { return xs.length - 1; }

    int writeIdx = 1;

    // 2,3,5,5,7,11,11,11,13
    // if copying to other array:
    // 2 3 5 - 7 11 - - 13
    // have 2 pointers -
    //   one for scanning input array
    //   other for writing to itself
    for (int i = 1; i < xs.length; i++) {
      int curr = xs[i];
      int previous = xs[i - 1];
      if (curr != previous) {
        xs[writeIdx] = curr;
        writeIdx++;
      }
    }

    return writeIdx;
  }

  private static void assertionStatus() {
    String status = DeleteDuplicates.class.desiredAssertionStatus() ? "enabled" : "disabled";
    System.out.println("Assertion: " + status);
    System.out.println("====================");
  }
}
