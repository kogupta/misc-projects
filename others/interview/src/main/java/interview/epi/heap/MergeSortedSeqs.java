package interview.epi.heap;

import interview.IntIterator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

import static interview.epi.array.SortFunctions.assertSorted;
import static interview.epi.array.SortFunctions.assertionStatus;

public final class MergeSortedSeqs {
  public static void main(String[] args) {
    assertionStatus();

    int[] sortSeq = {3, 5, 7};
    int[] sortSeq2 = {0, 6};
    int[] sortSeq3 = {0, 6, 28};
    List<IntIterator> seqs = List.of(IntArrayIterator.of(sortSeq),
        IntArrayIterator.of(sortSeq2),
        IntArrayIterator.of(sortSeq3));

    int[] result = mergeSortedSequences(seqs);
    System.out.println(Arrays.toString(result));

    assertSorted(result);
  }

  static int[] mergeSortedSequences(List<IntIterator> seqs) {
    PriorityQueue<Entry> minHeap = new PriorityQueue<>(Comparator.comparingInt(e -> e.value));
    for (int i = 0; i < seqs.size(); i++) {
      IntIterator seq = seqs.get(i);
      if (seq.hasNext()) {
        int v = seq.nextInt();
        minHeap.add(new Entry(v, i));
      }
    }

    IntStream.Builder builder = IntStream.builder();
    while (!minHeap.isEmpty()) {
      Entry entry = minHeap.remove();
      builder.add(entry.value);
      IntIterator itr = seqs.get(entry.arrayId);
      if (itr.hasNext()) {
        int v = itr.nextInt();
        Entry e = new Entry(v, entry.arrayId);
        minHeap.add(e);
      }
    }

    return builder.build().toArray();
  }

  private static final class Entry {
    final int value;
    final int arrayId;

    Entry(int value, int arrayId) {
      this.value = value;
      this.arrayId = arrayId;
    }
  }

  static final class IntArrayIterator implements IntIterator {
    private final int[] array;
    private int idx;

    IntArrayIterator(int[] array) {
      this.array = array;
    }

    @Override
    public int nextInt() {
      return array[idx++];
    }

    @Override
    public boolean hasNext() {
      return idx < array.length;
    }

    public static IntIterator of(int[] array) {
      return new IntArrayIterator(array);
    }
  }
}
