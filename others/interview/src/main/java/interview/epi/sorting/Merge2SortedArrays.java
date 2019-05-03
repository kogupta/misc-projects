package interview.epi.sorting;

import java.util.Arrays;

import static interview.epi.array.SortFunctions.assertionStatus;

// given: one sorted large array with ENOUGH empty slots at END + another sorted smaller array
// empty slots at end of larger array is enough to accommodate smaller array
//
// output: in place merge of these two sorted arrays - return sorted larger array
public final class Merge2SortedArrays {
  public static void main(String[] args) {
    assertionStatus();

    // 0 denotes empty!
    int[] xs = {5, 13, 17, 0, 0, 0, 0, 0};
    int[] ys = {3, 7, 11, 19};

    merge(xs, 3, ys, 4);
    System.out.println(Arrays.toString(xs));
    assert Arrays.equals(xs, new int[]{3, 5, 7, 11, 13, 17, 19, 0});
  }

  private static void merge(int[] xs, int lenX, int[] ys, int lenY) {
    int i = lenX - 1, j = lenY - 1, writeIdx = lenX + lenY - 1;
    while (i >= 0 && j >= 0) {
      xs[writeIdx--] = xs[i] > ys[j] ? xs[i--] : ys[j--];
    }

    while (j >= 0) {
      xs[writeIdx--] = ys[j--];
    }
  }
}
