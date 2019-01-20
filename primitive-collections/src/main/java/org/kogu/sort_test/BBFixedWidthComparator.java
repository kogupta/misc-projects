package org.kogu.sort_test;

import java.nio.ByteBuffer;

/**
 * Once fixed width comparable objects are stored in a byte buffer, use this
 * interface to compare them. Provide at which indices the objects start,
 * and then compare. Compare contract is same as {@link Comparable#compareTo(Object)}
 * method.
 */
@FunctionalInterface
public interface BBFixedWidthComparator {
  /**
   * Compare fixed width objects at specified instances in byte buffer.
   *
   * @param buffer      data storage
   * @param index       start index of first object
   * @param index2      start index of second object
   * @param recordWidth width of the object in bytes
   * @return int stating first object is less than, equal to or greater than second object.
   */
  int compareFixedWidthObjects(ByteBuffer buffer, int index, int index2, int recordWidth);

  BBFixedWidthComparator intComparator = (buffer, idx, idx2, $) -> {
    int width = Integer.BYTES;
    int a = buffer.getInt(idx * width);
    int b = buffer.getInt(idx2 * width);
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
