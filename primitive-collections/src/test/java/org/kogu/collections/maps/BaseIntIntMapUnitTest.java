package org.kogu.collections.maps;

import junit.framework.TestCase;
import org.kogu.collections.test_helper.BaseIntIntMapTest;
import org.kogu.collections.test_helper.IMapTest;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public abstract class BaseIntIntMapUnitTest extends TestCase {

  private final static float[] FILL_FACTORS = {0.25f, 0.5f, 0.75f, 0.9f, 0.99f};

  abstract protected IntIntMap makeMap(final int size, final float fillFactor);

  public void testPut() {
    for (final float ff : FILL_FACTORS)
      testPutHelper(ff);
  }

  private void testPutHelper(final float fillFactor) {
    final IntIntMap map = makeMap(100, fillFactor);
    for (int i = 0; i < 100_000; ++i) {
      map.justPut(i, i);
//      assertEquals(0, map.justPut(i, i));
      assertEquals(i + 1, map.size());
      assertEquals(i, map.getOrDefault(i, -1));
    }
    //now check the final state
    for (int i = 0; i < 100_000; ++i)
      assertEquals(i, map.getOrDefault(i, -1));
  }

  public void testPutNegative() {
    for (final float ff : FILL_FACTORS)
      testPutNegative(ff);
  }

  private void testPutNegative(final float fillFactor) {
    final IntIntMap map = makeMap(100, fillFactor);
    for (int i = 0; i < 100_000; ++i) {
      map.justPut(-i, -i);
      assertEquals(i + 1, map.size());
      assertEquals(-i, map.getOrDefault(-i, 1));
    }
    //now check the final state
    for (int i = 0; i < 100000; ++i)
      assertEquals(-i, map.getOrDefault(-i, 1));
  }

  public void testPutRandom() {
    for (final float ff : FILL_FACTORS)
      testPutRandom(ff);
  }

  private void testPutRandom(final float fillFactor) {
    final int SIZE = 100 * 1_000;
    final Set<Integer> set = new HashSet<>(SIZE);
    final int[] vals = new int[SIZE];
    while (set.size() < SIZE)
      set.add(ThreadLocalRandom.current().nextInt());
    int i = 0;
    for (final Integer v : set)
      vals[i++] = v;
    final IntIntMap map = makeMap(100, fillFactor);
    for (i = 0; i < vals.length; ++i) {
      map.justPut(vals[i], vals[i]);
//      assertEquals(0, map.put(vals[i], vals[i]));
      assertEquals(i + 1, map.size());
      assertEquals(vals[i], map.getOrDefault(vals[i], 0));
    }
    //now check the final state
    for (i = 0; i < vals.length; ++i)
      assertEquals(vals[i], map.getOrDefault(vals[i], 0));
  }

  public void testRemove() {
    for (final float ff : FILL_FACTORS)
      testRemoveHelper(ff);
  }

  private void testRemoveHelper(final float fillFactor) {
    final IntIntMap map = makeMap(100, fillFactor);
    int addCnt = 0, removeCnt = 0;
    for (int i = 0; i < 100_000; ++i) {
      map.justPut(addCnt, addCnt);
//      assertEquals(0, map.put(addCnt, addCnt));
      addCnt++;

      map.justPut(addCnt, addCnt);
//      assertEquals("Failed for addCnt = " + addCnt + ", ff = " + fillFactor, 0, map.put(addCnt, addCnt));
      addCnt++;

      map.justRemove(removeCnt);
//      assertEquals(removeCnt, map.remove(removeCnt));
      removeCnt++;

      assertEquals(i + 1, map.size()); //map grows by one element on each iteration
    }
    for (int i = removeCnt; i < addCnt; ++i)
      assertEquals(i, map.getOrDefault(i, -1));
  }

  public void test1() {
    final BaseIntIntMapTest test = new BaseIntIntMapTest() {
      @Override
      public IntIntMap makeMap(int size, float fillFactor) {
        return BaseIntIntMapUnitTest.this.makeMap(size, fillFactor);
      }
    };
    for (final float ff : FILL_FACTORS)
      testNHelper(ff, test.getTest());
  }

  public void test2() {
    final BaseIntIntMapTest test = new BaseIntIntMapTest() {
      @Override
      public IntIntMap makeMap(int size, float fillFactor) {
        return BaseIntIntMapUnitTest.this.makeMap(size, fillFactor);
      }
    };
    for (final float ff : FILL_FACTORS)
      testNHelper(ff, test.putTest());
  }

  public void test3() {
    final BaseIntIntMapTest test = new BaseIntIntMapTest() {
      @Override
      public IntIntMap makeMap(int size, float fillFactor) {
        return BaseIntIntMapUnitTest.this.makeMap(size, fillFactor);
      }
    };
    for (final float ff : FILL_FACTORS)
      testNHelper(ff, test.removeTest());
  }

  private void testNHelper(final float fillFactor, final IMapTest test1) {
    final int[] keys = TestUtils.KeyGenerator.getKeys(10000);
    test1.setup(keys, fillFactor, 2);
    test1.test();
  }
}