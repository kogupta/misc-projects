package org.kogu.sort_test;

import it.unimi.dsi.fastutil.ints.IntArrays;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

import static java.util.function.IntUnaryOperator.identity;
import static org.kogu.sort_test.BBFixedWidthComparator.intComparator;
import static org.kogu.sort_test.Functions.displayIntByteBuffer;

public final class TestBBSort {
  private static final int recordWidth = Integer.BYTES;

  public static void main(String[] args) {
    int[] sizes = {10, 15, 20, 30, 50, 100, 1_000, 10_000};

    for (int numItems : sizes) {
      System.out.println("items count: " + numItems);

      final int[] expected = orderedIntArray(numItems);
      ByteBuffer buffer = createIntData(expected);

      ByteBufferSort<Integer> sorter = new ByteBufferSort<>(buffer, intComparator, recordWidth);
      int[] sortedIndex = sorter.sort();

      int[] obtained = new int[numItems];
      for (int idx = 0; idx < sortedIndex.length; idx++) {
        int i = sortedIndex[idx];
        obtained[idx] = buffer.getInt(i * recordWidth);
      }

      if (!Arrays.equals(expected, obtained)) {
        System.err.println("Sorting failed!");
        System.err.println("Obtained: " + Arrays.toString(obtained));

        throw new AssertionError("Sorting failed!");
      }

//      System.out.println("Sorting works: obtained => " + Arrays.toString(obtained));
      System.out.println("Sorting works!");
    }


  }

  private static ByteBuffer createIntData(int[] expected) {
    IntArrays.shuffle(expected, new Random());

    ByteBuffer buffer = ByteBuffer.allocate(expected.length * Integer.BYTES);
    for (int i : expected) buffer.putInt(i);

    buffer.flip();
    displayIntByteBuffer(buffer.duplicate());

    IntArrays.quickSort(expected);

    return buffer;
  }

  private static int[] orderedIntArray(int length) {
    int[] ns = new int[length];
    Arrays.setAll(ns, identity());
    return ns;
  }

}
