package miscTests;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.lmdbjava.*;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static java.nio.ByteBuffer.allocateDirect;
import static java.nio.ByteOrder.BIG_ENDIAN;
import static java.nio.ByteOrder.nativeOrder;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.lmdbjava.DbiFlags.*;
import static org.lmdbjava.KeyRange.closed;

@RunWith(Parameterized.class)
public final class IntegerKeyTest {
  private static final boolean disableIteratorTest = false;

  private static final int keyCount = 10;

  @Rule
  public final TemporaryFolder tmp = new TemporaryFolder();

  @Parameterized.Parameter(0)
  public boolean hasIntegerKey;

  private ByteOrder keyByteOrder;
  private ByteBuffer KEY;
  private ByteBuffer VALUE;

  private Env<ByteBuffer> env;
  private Dbi<ByteBuffer> db;

  private long[] sortedKeys;

  @Parameterized.Parameters(name = "hasIntKey: {0}")
  public static Collection data() {
    Object[][] data = {{true}, {false}};
    return Arrays.asList(data);
  }

  @Before
  public void init() throws IOException {
    System.out.printf("---- init: hasIntKey => %s ----%n", hasIntegerKey);
    DbiFlags[] dbiFlags = hasIntegerKey ?
        new DbiFlags[]{MDB_CREATE, MDB_INTEGERKEY, MDB_DUPSORT} :
        new DbiFlags[]{MDB_CREATE, MDB_DUPSORT};

    File dir = tmp.newFolder();
    env = Env.create().open(dir);
    db = env.openDbi("int-test-db", dbiFlags);

    keyByteOrder = hasIntegerKey ? nativeOrder() : BIG_ENDIAN;
    KEY = allocateDirect(8).order(keyByteOrder);
    VALUE = allocateDirect(4).order(BIG_ENDIAN);

    sortedKeys = createSortedKeys();
    populateDB(sortedKeys);
  }

  @Test
  public void allKeys() {
    iteratorRangeTest(KeyRange.all(), sortedKeys.length);
  }

  @Test
  public void allKeysSparseRange() {
    long min = sortedKeys[0];
    long max = sortedKeys[sortedKeys.length - 1];
    cursorRangeTest(min, max * keyCount, sortedKeys.length);

    KeyRange<ByteBuffer> range = closed(bb(min), bb(max * keyCount));
    iteratorRangeTest(range, sortedKeys.length);
  }

  @Test
  public void allKeysExactRange() {
    long min = sortedKeys[0];
    long max = sortedKeys[sortedKeys.length - 1];
    cursorRangeTest(min, max, sortedKeys.length);

    KeyRange<ByteBuffer> range = closed(bb(min), bb(max));
    iteratorRangeTest(range, sortedKeys.length);
  }

  @Test
  public void subset() {
    long min = sortedKeys[0];
    long max = sortedKeys[sortedKeys.length - 1];

    System.out.println("---- 6 rows expected ----");
    cursorRangeTest(min, sortedKeys[5], 6);
    iteratorRangeTest(closed(bb(min), bb(sortedKeys[5])), 6);

    System.out.println("---- 4 rows expected ----");
    cursorRangeTest(sortedKeys[2], sortedKeys[5], 4);
    iteratorRangeTest(closed(bb(sortedKeys[2]), bb(sortedKeys[5])), 4);

    System.out.println("---- 1 row expected ----");
    cursorRangeTest(max, max * 2, 1);
    iteratorRangeTest(closed(bb(max), bb(max * 2)), 1);
  }

  @Test
  public void invalidRange() {
    long max = sortedKeys[sortedKeys.length - 1];

    cursorRangeTest(max + 1, max * 2, 0);
    iteratorRangeTest(closed(bb(max + 1), bb(max * 2)), 0);
  }

  private void populateDB(long[] keys) {
    shuffle(keys);
    for (int i = 0; i < keys.length; i++) {
      System.out.printf("Pair: %,d ==> %d%n", keys[i], i);
      add(keys[i], i);
    }

    Arrays.sort(sortedKeys);
  }

  private void iteratorRangeTest(KeyRange<ByteBuffer> range, int expected) {
    if (disableIteratorTest) return;

    int count = 0;
    try (Txn<ByteBuffer> txn = env.txnRead();
         CursorIterator<ByteBuffer> itr = db.iterate(txn, range)) {
      for (CursorIterator.KeyVal<ByteBuffer> kv : itr.iterable()) {
        count++;
      }
    }
    assertThat(count, is(expected));
  }

  private void cursorRangeTest(long from, long to, int expected) {
    assertThat(from, is(lessThan(to)));
    int count = 0;

    try (Txn<ByteBuffer> txn = env.txnRead();
         Cursor<ByteBuffer> cursor = db.openCursor(txn)) {
      ByteBuffer start = allocateDirect(8).order(keyByteOrder).putLong(from);
      start.flip();

      if (cursor.get(start, GetOp.MDB_SET_RANGE)) {
        long curr = cursor.key().order(keyByteOrder).getLong();

        for (boolean b = curr <= to; b;
             b = cursor.next() &&
                 (curr = cursor.key().order(keyByteOrder).getLong()) <= to) {
          System.out.printf("Current key: %,d%n", curr);

          count++;
        }
      }
    }

    assertThat(count, is(expected));
  }

  private void add(long key, int value) {
    KEY.clear();
    KEY.putLong(key).flip();

    VALUE.clear();
    VALUE.putInt(value).flip();

    db.put(KEY, VALUE);
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

  private ByteBuffer bb(long a) {
    ByteBuffer buffer = allocateDirect(8).order(keyByteOrder);
    buffer.putLong(a).flip();
    return buffer;
  }

}