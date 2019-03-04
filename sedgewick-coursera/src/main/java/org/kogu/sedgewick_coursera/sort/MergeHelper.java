package org.kogu.sedgewick_coursera.sort;

import java.util.Arrays;

import static org.kogu.sedgewick_coursera.sort.SortFunctions.assertSorted;

public enum MergeHelper {
  ;

  public static int[] merge(int[] left, int[] right) {
    assertSorted(left);
    assertSorted(right);
    return mergeRegions(
        left, 0, left.length - 1,
        right, 0, right.length - 1
    );
  }

  /**
   *
   * @param left left sorted array in typical diagrams
   * @param ls   left start index - inclusive
   * @param le   left end index   - inclusive
   * @param right right sorted array
   * @param rs    right start index - inclusive
   * @param re    right end index   - inclusive
   * @return     merged and sorted array
   */
  public static int[] mergeRegions(int[] left, int ls, int le,
                                   int[] right, int rs, int re) {
    assertSorted(left, ls, le);
    assertSorted(right, rs, re);

    int[] target = new int[(le + 1 - ls) + (re + 1 - rs)];

    int i = ls, j = rs, k = 0;
    for (; i <= le && j <= re; k++) {
      int a = left[i], b = right[j];
      if (a <= b) {
        target[k] = a;
        i++;
      } else {
        target[k] = b;
        j++;
      }
    }

    if (i <= le)
      System.arraycopy(left, i, target, k, le + 1 - i);

    if (j <= re)
      System.arraycopy(right, j, target, k, re + 1 - j);

    assertSorted(target);

    return target;
  }

  public static void merge(int[] arr, int[] aux,
                           int lo, int mid, int hi) {
    assertSorted(arr, lo, mid);
    assertSorted(arr, mid + 1, hi);

    System.arraycopy(arr, lo, aux, lo, hi - lo + 1);

    int i = lo, k = lo, j = mid + 1;
    for (; i <= mid && j <= hi; k++) {
      int a = aux[i], b = aux[j];
      if (a <= b) {
        arr[k] = a;
        i++;
      } else {
        arr[k] = b;
        j++;
      }
    }

    if (i <= mid) System.arraycopy(aux, i, arr, k, mid + 1 - i);
    if (j <= hi) System.arraycopy(aux, j, arr, k, hi + 1 - j);

    assertSorted(arr, lo, hi);
  }

  public static void merge2(int[] arr, int[] aux,
                            int lo, int mid, int hi) {
//    assertSorted(arr, lo, mid);
//    assertSorted(arr, mid + 1, hi);

    System.arraycopy(arr, lo, aux, lo, hi - lo + 1);

    int i = lo, k = lo, j = mid + 1;
    for (; k <= hi; k++) {
      if      (i > mid)         arr[k] = aux[j++];
      else if (j > hi)          arr[k] = aux[i++];
      else if (aux[j] < aux[i]) arr[k] = aux[j++];
      else                      arr[k] = aux[i++];
    }

//    assertSorted(arr, lo, hi);
  }

  public static void main(String[] args) {
    int[] xs = new int[10];
    Arrays.setAll(xs, i -> i);

    int[] merged = merge(xs, xs);
    System.out.println(Arrays.toString(merged));

    int[] xs2 = mergeRegions(
        xs, 1, xs.length - 1,         // exclude 1st item, ie 0
        xs, 0, xs.length - 2         // exclude last item, ie 9
    );

    // expected - only one 0 and 9
    System.out.println(Arrays.toString(xs2));

    xs = new int[20];
    Arrays.setAll(xs, i -> i % 10);
    merge(xs, new int[20], 0, 9, 19);
    assertSorted(xs);

    xs = new int[20];
    Arrays.setAll(xs, i -> i % 10);
    merge2(xs, new int[20], 0, 9, 19);
    assertSorted(xs);


  }
}
