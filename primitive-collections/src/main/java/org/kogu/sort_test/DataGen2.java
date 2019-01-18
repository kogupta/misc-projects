package org.kogu.sort_test;

import it.unimi.dsi.fastutil.ints.IntArrays;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.US_ASCII;

public final class DataGen2 {
  private static final Random random = new Random();
  private static final int recordWidth = 44;
  private static final String2Bytes extractor = new String2Bytes(36, US_ASCII);
  private static final ObjectExtractor<ImmutableRow> rowExtractor = (buffer, index, recordWidth) -> {
    String s = extractor.readString(buffer);
    long n = buffer.getLong();
    return ImmutableRow.of(s, n);
  };

  private static final long zero = initZero();

  private static ImmutableRow expectedMin, expectedMax;
  private static ByteBuffer data;
  private static int[] index;

  public static void main(String[] args) {
//    int count = (int) TimeUnit.HOURS.toMillis(1);
    int count = 100;

    createIndex(count);
    createData(count);

    // does it really look as expected?
//    iterate(data.duplicate(), recordWidth, DataGen2::consume);

    // need more buffer flipping?
//    printState(data);


  }

  private static void createIndex(int count) {
    index = new int[count];
    Arrays.setAll(index, i -> i);

    int c = 0;
    while (index[0] == 0 || index[count - 1] == count - 1) {
      IntArrays.shuffle(index, random);
      System.out.println("Shuffle count: " + ++c);
    }

    System.out.println(Arrays.toString(index));
  }

  private static void createData(int count) {
    data = ByteBuffer.allocate(count * recordWidth);
    for (int offset : index) {
      String s = UUID.randomUUID().toString();
      byte[] bytes = extractor.extractBytes(s);

      long n = zero + offset;
      data.put(bytes);
      data.putLong(n);

      // set baseline for assertion
      if (expectedMax == null || expectedMin == null) {
        if (offset == 0) {
          expectedMin = ImmutableRow.of(s, n);
        } else if (offset == count - 1) {
          expectedMax = ImmutableRow.of(s, n);
        }
      }
    }

    data.flip();

    System.out.printf("Data generated ... # of elements: %,d%n", count);
  }

  private static void consume(ByteBuffer buffer) {
//    System.out.println(state(buffer));
    String s = extractor.readString(buffer);
    long n = buffer.getLong() - zero;
    System.out.println("key: " + s + ", value: " + n);
    System.out.println();
  }

  private static long initZero() {
    LocalDateTime start = LocalDateTime.of(2018, Month.JANUARY, 1, 5, 0);
    return start.atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
  }
}
