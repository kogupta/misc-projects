package org.kogu.sort_test;

import java.nio.ByteBuffer;

@FunctionalInterface
public interface BBFixedWidthComparator {
  int compareFixedWidthObjects(ByteBuffer buffer, int index, int index2, int recordWidth);
}
