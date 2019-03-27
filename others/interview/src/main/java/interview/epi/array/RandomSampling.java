package interview.epi.array;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public final class RandomSampling {
  public static void main(String[] args) {

  }

  public static <T> List<T> sample(Iterator<T> stream, int k) {
    List<T> result = new ArrayList<>(k);

    // read first k elements
    for (; stream.hasNext() && result.size() < k; ) {
      result.add(stream.next());
    }

    int seenSoFar = k;
    //noinspection LongLiteralEndingWithLowercaseL
    Random r = new Random(31012010l);

    // Generate a random number in [0 , numSeenSoFar] , and if this number is in
    // [0, k - 1], we replace that element from the sample with x.
    while (stream.hasNext()) {
      T next = stream.next();
      seenSoFar++;
      int nextIdx = r.nextInt(seenSoFar);
      if (nextIdx < k) {
        result.set(nextIdx, next);
      }
    }

    return result;
  }
}
