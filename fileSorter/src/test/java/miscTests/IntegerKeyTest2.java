package miscTests;

import org.junit.Test;
import org.lmdbjava.*;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Random;

import static java.nio.ByteBuffer.allocateDirect;
import static java.nio.ByteOrder.BIG_ENDIAN;
import static java.nio.ByteOrder.nativeOrder;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.lmdbjava.DbiFlags.*;

public final class IntegerKeyTest2 {
  private static final int keyCount = 10;
  private static final boolean hasIntKey = false;
  private static final ByteOrder byteOrder = BIG_ENDIAN;
  private static final ByteOrder keyByteOrder = hasIntKey ? nativeOrder() : BIG_ENDIAN;

  private final ByteBuffer KEY = allocateDirect(8).order(keyByteOrder);
  private final ByteBuffer VALUE = allocateDirect(4).order(byteOrder);

  private final Env<ByteBuffer> env;
  private final Dbi<ByteBuffer> db;

  public IntegerKeyTest2() throws IOException {
    File dir = Files.createTempDirectory(IntegerKeyTest2.class.getSimpleName()).toFile();
    env = Env.create().open(dir);
    DbiFlags[] dbiFlags = hasIntKey ?
        new DbiFlags[]{MDB_CREATE, MDB_INTEGERKEY, MDB_DUPSORT} :
        new DbiFlags[]{MDB_CREATE, MDB_DUPSORT};
    db = env.openDbi(IntegerKeyTest2.class.getSimpleName(), dbiFlags);
  }

  @Test
  public void execute() {
    long[] keys = createSortedKeys();
    shuffle(keys);
    for (int i = 0; i < keys.length; i++) {
      System.out.printf("Pair: %,d ==> %d%n", keys[i], i);
      add(keys[i], i);
    }

    Arrays.sort(keys);
    rangeTest(KeyRange.all(), keys.length);

    long min = keys[0];
    long max = keys[keys.length - 1];

    System.out.println("---- all, sparse range ----");

    KeyRange<ByteBuffer> range = KeyRange.closed(bb(min), bb(max * keyCount));
//    rangeTest(range, keys.length);
    rangeTest2(min, max * keyCount, keys.length);
//    closedRangeIteration(range, keys.length);

    System.out.println("---- all, exact range ----");

    KeyRange<ByteBuffer> range2 = KeyRange.closed(bb(min), bb(max));
//    rangeTest(range2, keys.length); // fails for this case
    rangeTest2(min, max, keys.length);
//    closedRangeIteration(range2, keys.length);

    System.out.println("---- 6 rows expected ----");

    rangeTest2(min, keys[5], 6);

    System.out.println("---- 4 rows expected ----");

    rangeTest2(keys[2], keys[5], 4);

    System.out.println("---- 1 row expected ----");

    rangeTest2(max, max * 2, 1);

    System.out.println("---- no rows expected ----");

    rangeTest2(max + 1, max * 2, 0);
  }

  private long[] keys() {
    long[] keys = createSortedKeys();
    shuffle(keys);
    for (int i = 0; i < keys.length; i++) {
      System.out.printf("Pair: %,d ==> %d%n", keys[i], i);
      add(keys[i], i);
    }

    Arrays.sort(keys);
    return keys;
  }

  private void rangeTest(KeyRange<ByteBuffer> range, int expected) {
    try (Txn<ByteBuffer> txn = env.txnRead();
         CursorIterator<ByteBuffer> itr = db.iterate(txn, range)) {
      int count = 0;
      for (CursorIterator.KeyVal<ByteBuffer> kv : itr.iterable()) {
        count++;
      }

      assertThat(count, is(expected));
    }
  }

//  for(x(); y(); z()) {
//    a();
//  }
//
//  x();
//  while(y()) {
//    a();
//    z();
//  }
//
//  x();
//  if(y()) {
//    do {
//      a();
//      z();
//    } while(y())
//  }

  private void rangeTest2(long from, long to, int expected) {
    assertThat(from, is(lessThan(to)));
    int count = 0;

//    try (Txn<ByteBuffer> txn = env.txnRead();
//         Cursor<ByteBuffer> cursor = db.openCursor(txn)) {
//    ByteBuffer start = allocateDirect(8).order(keyByteOrder).putLong(from);
//    start.flip();
//
//      if (cursor.get(start, GetOp.MDB_SET_RANGE)) {
//        long curr = cursor.key().order(keyByteOrder).getLong();
//        boolean b = curr <= to;
//        while (b) {
//          System.out.printf("Current key: %,d%n", curr);
//
//          count++;
//
//          b = cursor.next() && (curr =cursor.key().order(keyByteOrder).getLong()) <= to;
//        }
////        do {
////          System.out.printf("Current key: %,d%n", curr);
////
////          if (curr <= to) {
////            count++;
////          }
////        } while (cursor.next() && cursor.key().order(keyByteOrder).getLong() <= to);
//      }
//    }

    try (Txn<ByteBuffer> txn = env.txnRead();
         Cursor<ByteBuffer> cursor = db.openCursor(txn)) {
      ByteBuffer start = allocateDirect(8).order(keyByteOrder).putLong(from);
      start.flip();

      if (cursor.get(start, GetOp.MDB_SET_RANGE)) {
        long curr = cursor.key().order(keyByteOrder).getLong();

        for (boolean b = curr <= to;
             b;
             b = cursor.next() && (curr = cursor.key().order(keyByteOrder).getLong()) <= to) {
          System.out.printf("Current key: %,d%n", curr);

          count++;
        }
      }
    }

    assertThat(count, is(expected));
  }

  private static long[] createSortedKeys() {
    long[] xs = new long[keyCount];
    LocalDateTime start = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0);
    for (int i = 0; i < xs.length; i++) {
      LocalDateTime time = start.plusHours(i);
      long millis = time.toInstant(ZoneOffset.UTC).toEpochMilli();
      xs[i] = millis;
    }

    return xs;
  }

  private void add(long key, int value) {
    KEY.clear();
    KEY.putLong(key).flip();

    VALUE.clear();
    VALUE.putInt(value).flip();

    db.put(KEY, VALUE);
  }

  private static ByteBuffer bb(long a) {
    ByteBuffer buffer = allocateDirect(8).order(keyByteOrder);
    buffer.putLong(a).flip();
    return buffer;
  }

  private static void shuffle(long[] arr) {
    Random rnd = new Random();

    for (int i = arr.length; i > 1; i--)
      swap(arr, i - 1, rnd.nextInt(i));
  }

  private static void swap(long[] arr, int i, int j) {
    long tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }
}