package org.kogu.sort_test;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

enum Functions {
  ;

  public static void iterate(ByteBuffer buffer, int stride, Consumer<ByteBuffer> consumer) {
    printState(buffer);
    int end = buffer.limit();
    for (int i = 0; i < end; i = buffer.limit()) {
      buffer.position(i).limit(i + stride);
      consumer.accept(buffer.asReadOnlyBuffer());
    }

    buffer.flip();
  }

  public static void displayIntByteBuffer(ByteBuffer buffer) {
    Consumer<ByteBuffer> c = bb -> System.out.print(bb.getInt() + " ");

    iterate(buffer, Integer.BYTES, c);
    System.out.println();
  }

  public static void printState(ByteBuffer bb) {
    String s = String.format("direct: %s, position: %,d, limit: %,d, capacity: %,d",
                             bb.isDirect(), bb.position(), bb.limit(), bb.capacity());
    System.out.println(s);
  }

  public static void time(String s, Runnable r) {
    long t0 = System.nanoTime();
    r.run();
    long timeTaken = System.nanoTime() - t0;
    System.out.printf("%s: %,d millis%n", s, TimeUnit.NANOSECONDS.toMillis(timeTaken));
  }
}
