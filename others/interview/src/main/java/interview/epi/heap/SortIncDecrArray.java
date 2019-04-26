package interview.epi.heap;

import interview.IntIterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static interview.epi.array.SortFunctions.*;

// given: a k increasing-decreasing array
// output: sort the array
//
// example: a 4-increasing decreasing array
// 57 -> 493, 493 <- 221, 221 -> 452, 452 <- 190
public final class SortIncDecrArray {
  public static void main(String[] args) {
    assertionStatus();

    // a 4-increasing decreasing array
    // 57 -> 493, 493 <- 221, 221 -> 452, 452 <- 190
    int[] xs = {57, 131, 493, 294, 221, 339, 418, 452, 442, 190};

    List<IntIterator> seqs = new ArrayList<>();

    SeqType seqType = SeqType.Increasing;
    int startIdx = 0;
    for (int i = 1; i <= xs.length; i++) {
      int prev = xs[i - 1];
      if (i == xs.length ||
          (prev < xs[i] && seqType == SeqType.Decreasing) ||
          (prev >= xs[i] && seqType == SeqType.Increasing)) {
        if (seqType == SeqType.Decreasing) {
          reverse(xs, startIdx, i - 1);
        }

        seqs.add(new IntArrayItr(xs, startIdx, i - 1));

        seqType = seqType.invert();
        startIdx = i;
      }
    }

    seqs.forEach(System.out::println);

    // NOTE: this is NOT IN-PLACE sort
    int[] ints = MergeSortedSeqs.mergeSortedSequences(seqs);
    System.out.println(Arrays.toString(ints));
    assertSorted(ints);
  }

  private static void reverse(int[] array, int from, int to) {
    for (int i = from, j = to; i < j; i++, j--)
      swap(array, i, j);
  }

  private enum SeqType {
    Increasing {
      @Override public SeqType invert() { return Decreasing;}
    },
    Decreasing {
      @Override public SeqType invert() { return Increasing;}
    };

    public abstract SeqType invert();
  }

  private static final class IntArrayItr implements IntIterator {
    private final int[] array;
    private final int from, to;
    private int idx;

    public IntArrayItr(int[] array, int from, int to) {
      this.array = array;
      this.from = from;
      this.to = to;
      this.idx = from;
    }

    @Override
    public int nextInt() {
      return array[idx++];
    }

    @Override
    public boolean hasNext() {
      return from <= idx && idx <= to;
    }

    @Override
    public String toString() {
      return String.format("from: %d, curr: %d, to: %d", from, idx, to);
    }
  }
}
