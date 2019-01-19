package org.kogu.sort_test;

import java.nio.ByteBuffer;

@FunctionalInterface
public interface BBFixedWidthComparator {
  int compareFixedWidthObjects(ByteBuffer buffer, int index, int index2, int recordWidth);

  BBFixedWidthComparator intComparator = (buffer, idx, idx2, $) -> {
//    System.out.println("---- int comparator ----");
//      Functions.printState(buffer);
    int width = Integer.BYTES;
    int a = buffer.getInt(idx * width);
    int b = buffer.getInt(idx2 * width);
//      Functions.printState(buffer);
//    System.out.printf("Comparing %d[%d] vs %d[%d]%n", a, idx, b, idx2);
    return Integer.compare(a, b);
  };

  BBFixedWidthComparator longComparator = (buffer, idx, idx2, $) -> {
    long a = buffer.getLong(idx * Long.BYTES);
    long b = buffer.getLong(idx2 * Long.BYTES);
    return Long.compare(a, b);
  };

  BBFixedWidthComparator rowComparator = (buffer, idx, idx2, width) -> {
    int offset = width - Long.BYTES;
    long a = buffer.getLong(idx * width + offset);
    long b = buffer.getLong(idx2 * width + offset);
    return Long.compare(a, b);
  };
}
