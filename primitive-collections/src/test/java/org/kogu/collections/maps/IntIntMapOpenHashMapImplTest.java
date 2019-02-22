package org.kogu.collections.maps;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.kogu.collections.maps.Tools.shuffle;

// TODO: parameterized tests
public class IntIntMapOpenHashMapImplTest {
  private static final int defValue = -1;

  private IntIntMap map;

  private static int[] intArray(int item) {
    int[] xs = new int[item];
    Arrays.setAll(xs, i -> i);
    return xs;
  }

  private static void put(IntIntMap map, int n) {
    map.justPut(n, n);
    assertTrue(map.containsKey(n));
    assertTrue(map.containsValue(n));
    assertEquals(n, map.getOrDefault(n, defValue));
  }

  private static void put(IntIntMap map, int key, int value) {
    map.justPut(key, value);
    assertTrue(map.containsKey(key));
    assertTrue(map.containsValue(value));
    assertEquals(value, map.getOrDefault(key, defValue));
  }

  private static void remove(IntIntMap map, int key) {
    map.justRemove(key);
    assertFalse(map.containsKey(key));
    assertFalse(map.containsValue(key));
    assertEquals(defValue, map.getOrDefault(key, defValue));
  }

  private static void clear(IntIntMap map) {
    assertNotNull(map);
    map.clear();
    assertTrue(map.isEmpty());
    assertEquals(0, map.size());
    assertEquals(defValue, map.getOrDefault(0, defValue));
  }

  @Before
  public void init() {
    map = new IntIntMapOpenHashMapImpl(12, 0.75f);
  }

  @Test
  public void empty_map() {
    assertTrue(map.isEmpty());
    assertEquals(0, map.size());
    for (int i = 0; i < 1_000; i++) {
      assertEquals(defValue, map.getOrDefault(i, defValue));
      assertEquals(defValue, map.getOrDefault(-i, defValue));
    }

    for (int i = 0; i < 1_000; i++) {
      remove(map, i);
    }
  }

  @Test
  public void put_get() {
    int[] items = {10, 20, 50, 100, 1_000, 10_000};

    for (int item : items) {
      for (int key = 0; key < item; key++)
        put(map, key);

      assertFalse(map.isEmpty());
      assertEquals(item, map.size());

      for (int i = 0; i < item; i++) {
        int got = map.getOrDefault(i, defValue);
        assertEquals(i, got);
      }
    }
  }

  @Test
  public void put_get_negative_key() {
    int[] items = {10, 20, 50, 100, 1_000, 10_000};

    for (int item : items) {
      for (int key = 0; key < item; key++)
        put(map, -key);

      assertFalse(map.isEmpty());
      assertEquals(item, map.size());

      for (int i = 0; i < item; i++) {
        int got = map.getOrDefault(-i, 1);
        assertEquals(-i, got);
      }
    }
  }

  @Test
  public void put_get_negative_value() {
    int[] items = {10, 20, 50, 100, 1_000, 10_000};

    for (int item : items) {
      for (int key = 0; key < item; key++)
        put(map, key, -key);

      assertFalse(map.isEmpty());
      assertEquals(item, map.size());

      for (int i = 0; i < item; i++) {
        int got = map.getOrDefault(i, 1);
        assertEquals(-i, got);
      }
    }
  }

  @Test
  public void repeated_put_size_unchanged() {
    int[] items = {10, 20, 50, 100, 1_000, 10_000};

    for (int item : items) {
      map.clear();
      assertTrue(map.isEmpty());
      assertEquals(0, map.size());

      int[] xs = intArray(item);

      for (int i = 0; i < 10; i++) {
        shuffle(xs);
        for (int x : xs) map.justPut(x, x);
        assertFalse(map.isEmpty());
        assertEquals(item, map.size());
      }

      for (int i = 0; i < item; i++) {
        int got = map.getOrDefault(i, defValue);
        assertEquals(i, got);
      }
    }
  }

  @Test
  public void remove() {
    int[] items = {10, 20, 50, 100, 1_000, 10_000};

    for (int item : items) {
      for (int key = 0; key < item; key++) {
        map.justPut(key, key);
        remove(map, key);
        remove(map, key);
        remove(map, key);
        assertTrue(map.isEmpty());
      }
    }
  }

  @Test
  public void remove2() {
    int[] items = {10, 20, 50, 100, 1_000, 10_000};

    for (int item : items) {
      clear(map);
      int[] ns = intArray(item);
      shuffle(ns);

      for (int i = 0; i < ns.length; i++) {
        int n = ns[i];
        put(map, n);
        assertEquals(i + 1, map.size());
      }

      assertEquals(item, map.size());

      shuffle(ns);
      for (int i = 0; i < ns.length; i++) {
        int n = ns[i];
        remove(map, n);
        assertEquals(item - i - 1, map.size());
      }

      assertTrue(map.isEmpty());
    }
  }
}