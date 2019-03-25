package interview.epi.array;

import java.util.Arrays;

import static interview.epi.array.SortFunctions.*;

/**
 * Given an array of 0s, 1s and 2s - sort the array
 */
public final class DutchNatFlag {
  private static final int count = 10_000;

  public static void main(String[] args) {
    simpleCountingSolution(createInput(10));
    threeWayPartition(createInput(10));

//    unscientificBenchmark(1_000, count);
  }

  @SuppressWarnings("SameParameterValue")
  private static void unscientificBenchmark(int iterations, int arraySize) {
    long a = 0, b = 0;
    for (int i = 0; i < iterations; i++) {
      long t0 = System.nanoTime();
      simpleCountingSolution(createInput(arraySize));
      long t1 = System.nanoTime();
      threeWayPartition(createInput(arraySize));
      long t2 = System.nanoTime();

      a += (t1 - t0);
      b += (t2 - t1);

      System.gc();
    }

    double da = (double) a / iterations;
    double db = (double) b / iterations;
    System.out.printf("Time taken nanos: %,.2f -vs- %,.2f%n", da, db);
  }

  private static void threeWayPartition(int[] xs) {
    // 3 way partitioning in qsort
    // before: [ v | .....|  ]
    //          -lo-      -hi-
    // after: [ < v | ...  | = v  ... |  > v ]
    //          -lo-      -lt-       -gt-   -hi-

    final int lo = 0, hi = xs.length - 1;
    int lt = lo, gt = hi;

    //    int v = xs[lo];
    int v = 1;

    // 0, 1, 0, 2, 1, 1, 2, 2, 0, 0
    for (int i = lo; i <= hi && i <= gt;) {
      System.out.println("Curr state: " + Arrays.toString(xs));
      System.out.printf("i: %d, lt: %d, gt: %d%n", i, lt, gt);
      System.out.println();
      int element = xs[i];
      if (element < v) {
        swap(xs, i, lt);
        lt++;
        i++;
      } else if (element > v) {
        swap(xs, i, gt);
        gt--;
      } else {
        // element == v, partitioning element
        i++;
      }
    }

//    System.out.println(Arrays.toString(xs));
    assertSorted(xs, lo, hi);
  }

  private static void simpleCountingSolution(int[] xs) {
    // trivial solution
    int zCount = 0, oneCount = 0;
    for (int x : xs) {
      if (x == 0) { zCount++; }
      else if (x == 1) { oneCount++; }
    }

    for (int i = 0; i < zCount; i++) xs[i] = 0;
    for (int i = zCount; i < zCount + oneCount; i++) xs[i] = 1;
    for (int i = zCount + oneCount; i < xs.length; i++) xs[i] = 2;

//    System.out.println(Arrays.toString(xs));
    assertSorted(xs);
  }

  private static int[] createInput(int count) {
    int[] xs = new int[count];
    Arrays.setAll(xs, i -> i % 3);
    shuffle(xs);
//    System.out.println(Arrays.toString(xs));
    return xs;
  }
}
