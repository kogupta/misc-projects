package org.kogu.sort_test;

import it.unimi.dsi.fastutil.ints.IntArrays;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

import static org.kogu.sort_test.BBFixedWidthComparator.intComparator;
import static org.kogu.sort_test.Functions.printState;
import static org.kogu.sort_test.ObjectExtractor.intExtractor;

public final class TestBBSort {
  private static final int numItems = 15;

  public static void main(String[] args) {
    int[] index = createIndex(numItems);
    ByteBuffer buffer = createData(index);
    int recordWidth = Integer.BYTES;

    ByteBufferSort<Integer> sorter = new ByteBufferSort<>(buffer, index, intExtractor, intComparator, recordWidth);
    sorter.quickSort(0, index.length);
//    sorter.selectionSort(0, index.length);

    System.out.println();

    System.out.println("Post sorting: ");
    System.out.println("Index: " + Arrays.toString(index));
    int[] obtained = new int[index.length];
    for (int idx = 0; idx < index.length; idx++) {
      int i = index[idx];
      obtained[idx] = intExtractor.intAtIndex(buffer, i);
    }

    IntArrays.quickSort(index);
    if (!Arrays.equals(index, obtained)) {
      System.err.println("Sorting failed!");
      System.err.println("Expected: " + Arrays.toString(index));
      System.err.println("Obtained: " + Arrays.toString(obtained));

      throw new AssertionError("Sorting failed!");
    }

//    System.out.println("Sorting works: obtained => " + Arrays.toString(obtained));
    System.out.println("Sorting works!");
  }

  private static ByteBuffer createData(int[] index) {
    final int width = Integer.BYTES;
    ByteBuffer buffer = ByteBuffer.allocate(index.length * width);
    for (int i : index) {
      buffer.putInt(i);
    }

    buffer.flip();

    // display
    Functions.iterate(buffer.duplicate(), width, bb -> System.out.print(bb.getInt() + " "));
    System.out.println();
    printState(buffer);

    return buffer;
  }

  private static int[] createIndex(int numItems) {
    int[] index = new int[numItems];
    Arrays.setAll(index, i -> i);
    IntArrays.shuffle(index, new Random());
    System.out.println("Index: " + Arrays.toString(index));
    return index;
  }
}
