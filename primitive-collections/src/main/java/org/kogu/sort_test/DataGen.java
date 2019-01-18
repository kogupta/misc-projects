package org.kogu.sort_test;

import it.unimi.dsi.fastutil.objects.ObjectArrays;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public final class DataGen {
  private static final Random random = new Random();

  private static ImmutableRow expectedMin, expectedMax;

  public static void main(String[] args) {
    LocalDateTime start = LocalDateTime.of(2018, Month.JANUARY, 1, 5, 0);

    long from = start.atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
    int count = (int) TimeUnit.HOURS.toMillis(1);
    long to = from + count;

    ImmutableRow[] rows = new ImmutableRow[count];

    int index = 0;

    for (long n = from; n < to; n++) {
      String s = UUID.randomUUID().toString();
      ImmutableRow row = new ImmutableRow(s, n);
      rows[index++] = row;
    }

    System.out.printf("Data generated ... # of elements: %,d%n", rows.length);

    expectedMin = ImmutableRow.of(rows[0].key, from);
    expectedMax = ImmutableRow.of(rows[rows.length - 1].key, to - 1);

    sort(rows, "QuickSort", () -> ObjectArrays.quickSort(rows));
    sort(rows, "MergeSort", () -> ObjectArrays.mergeSort(rows));
  }

  private static void sort(ImmutableRow[] rows, String message, Runnable r) {
    for (int i = 0; i < 10; i++) {
//      time("Shuffled: ", () -> ObjectArrays.shuffle(rows, new Random()));
      ObjectArrays.shuffle(rows, random);
      time(message, r);

      assert rows[0].equals(expectedMin);
      assert rows[rows.length - 1].equals(expectedMax);

      System.out.println("----");
    }
  }

  private static void time(String s, Runnable r) {
    long t0 = System.nanoTime();
    r.run();
    long timeTaken = System.nanoTime() - t0;
    System.out.printf("%s: %,d millis%n", s, TimeUnit.NANOSECONDS.toMillis(timeTaken));
  }
}
