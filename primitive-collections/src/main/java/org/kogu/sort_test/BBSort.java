package org.kogu.sort_test;

import java.nio.ByteBuffer;

public final class BBSort {
  private static final int recordWidth = 4;
  private static byte[] temp = new byte[recordWidth];
  private static byte[] temp2 = new byte[recordWidth];

  public static void main(String[] args) {
    ByteBuffer buffer = ByteBuffer.allocate(4 * 10);
    for (int i = 9; i >= 0; i--) {
      buffer.putInt(i);
    }

    buffer.flip();

    selectionSort(buffer, 0, 40);

    while (buffer.hasRemaining()) {
      System.out.println(buffer.getInt());
    }
  }

  private static <T> void sort(ByteBuffer buffer) {

  }

  @SuppressWarnings("unchecked")
  private static <K> void selectionSort(ByteBuffer a, int from, int to) {
    for (int i = from; i < to - 1; i += recordWidth) {
      int m = i;
      for (int j = i + recordWidth; j < to; j += recordWidth)
        if ((compareSection(a, j, m) < 0))
          m = j;
      if (m != i) {
        swap(a, i, m);
      }
    }
  }

  private static int compareSection(ByteBuffer bb, int a, int b) {
    int i = bb.getInt(a);
    int j = bb.getInt(b);
    return Integer.compare(i, j);
  }

  private static void swap(ByteBuffer buffer, int a, int b) {
//    final K u = a[i];
//    a[i] = a[m];
//    a[m] = u;

    int oldPos = buffer.position();
    int oldLimit = buffer.limit();
    buffer.position(a);
    buffer.get(temp);
    buffer.position(b);
    buffer.get(temp2);
    buffer.position(a);
    buffer.put(temp2);
    buffer.position(b);
    buffer.put(temp);

    buffer.position(oldPos);
    buffer.limit(oldLimit);
  }

}
