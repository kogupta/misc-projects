package interview.epi.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

import static interview.epi.array.SortFunctions.assertSorted;
import static interview.epi.array.SortFunctions.assertionStatus;

// k sorted array: array where final sorted position is at most k away
// 2 sorted array: 3, -1, 2, 6, 4, 5, 8
public final class ApproxSortedData {
  public static void main(String[] args) {
    assertionStatus();

    int[] xs = {3, -1, 2, 6, 4, 5, 8};
    sort(xs, 2);
    System.out.println(Arrays.toString(xs));
    assertSorted(xs);
  }

  private static void sort(int[] array, int k) {
    assert array.length > k;

    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    for (int i = 0; i < k; i++)
      minHeap.add(array[i]);

    for (int i = k; i < array.length; i++) {
      int n = minHeap.remove();
      array[i - k] = n;
      minHeap.add(array[i]);
    }

    int idx = array.length - k;
    while (!minHeap.isEmpty()) {
      array[idx++] = minHeap.remove();
    }
  }
}
