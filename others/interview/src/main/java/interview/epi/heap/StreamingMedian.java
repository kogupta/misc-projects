package interview.epi.heap;

import interview.IntIterator;
import interview.epi.heap.MergeSortedSeqs.IntArrayIterator;

import java.util.Comparator;
import java.util.PriorityQueue;

import static interview.epi.array.SortFunctions.assertionStatus;

public final class StreamingMedian {
  private static final int capacity = 16;

  public static void main(String[] args) {
    assertionStatus();

    median(new IntArrayIterator(new int[]{1}));
    median(new IntArrayIterator(new int[]{1, 0}));
    median(new IntArrayIterator(new int[]{1, 0, 3}));
    median(new IntArrayIterator(new int[]{1, 0, 3, 5}));
    median(new IntArrayIterator(new int[]{1, 0, 3, 5, 2}));
    median(new IntArrayIterator(new int[]{1, 0, 3, 5, 2, 0}));
    median(new IntArrayIterator(new int[]{1, 0, 3, 5, 2, 0, 1}));
  }

  private static void median(IntIterator ints) {
    PriorityQueue<Integer> min = new PriorityQueue<>(capacity);
    PriorityQueue<Integer> max = new PriorityQueue<>(capacity, Comparator.reverseOrder());

    while (ints.hasNext()) {
      int n = ints.nextInt();

      if (min.isEmpty()) {
        min.add(n);
      } else {
        if (n >= min.peek()) min.add(n);
        else max.add(n);
      }

      if (min.size() > max.size() + 1) {
        max.add(min.remove());
      } else if (max.size() > min.size()) {
        min.add(max.remove());
      }
    }

    double median = min.size() == max.size() ?
        0.5 * (min.peek() + max.peek()) :
        min.peek();
    System.out.println("median: " + median);
  }
}
