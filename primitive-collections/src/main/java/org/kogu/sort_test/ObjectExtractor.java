package org.kogu.sort_test;

import java.nio.ByteBuffer;

@FunctionalInterface
public interface ObjectExtractor<T> {
  T objectAtIndex(ByteBuffer buffer, int index, int recordWidth);

  final class IntExtractor implements ObjectExtractor<Integer> {
    @Override
    public Integer objectAtIndex(ByteBuffer buffer, int index, int recordWidth) {
      return intAtIndex(buffer, index);
    }

    int intAtIndex(ByteBuffer buffer, int index) {
      return buffer.getInt(index * Integer.BYTES);
    }
  }

  IntExtractor intExtractor = new IntExtractor();

  final class LongExtractor implements ObjectExtractor<Long> {
    @Override
    public Long objectAtIndex(ByteBuffer buffer, int index, int recordWidth) {
      return longAtIndex(buffer, index);
    }

    long longAtIndex(ByteBuffer buffer, int index) {
      return buffer.getLong(index * Long.BYTES);
    }
  }

  LongExtractor longExtractor = new LongExtractor();
}
