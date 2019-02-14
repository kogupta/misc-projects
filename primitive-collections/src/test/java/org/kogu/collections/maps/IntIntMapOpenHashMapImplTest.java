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
      map.justRemove(i);
      assertEquals(defValue, map.getOrDefault(i, defValue));
      assertEquals(defValue, map.getOrDefault(-i, defValue));
    }
  }

  @Test
  public void put_get() {
    int[] items = {10, 20, 50, 100, 1_000, 10_000};

    for (int item : items) {
      System.out.println("---- item: " + item + " ----");
      for (int key = 0; key < item; key++) {
        map.justPut(key, key);
      }

      assertFalse(map.isEmpty());
      assertEquals(item, map.size());

      for (int i = 0; i < item; i++) {
        int got = map.getOrDefault(i, defValue);
        assertEquals(i, got);
      }
    }
  }

  @Test
  public void put_get_negative() {
    int[] items = {10, 20, 50, 100, 1_000, 10_000};

    for (int item : items) {
      System.out.println("---- item: " + item + " ----");
      for (int key = 0; key < item; key++) {
        map.justPut(key, -key);
      }

      assertFalse(map.isEmpty());
      assertEquals(item, map.size());

      for (int i = 0; i < item; i++) {
        int got = map.getOrDefault(i, defValue);
        assertEquals(-i, got);
      }
    }
  }

  @Test
  public void repeated_put_size_unchanged() {
    int[] items = {10, 20, 50, 100, 1_000, 10_000};

    for (int item : items) {
      System.out.println("---- item: " + item + " ----");
      map.clear();
      assertTrue(map.isEmpty());
      assertEquals(0, map.size());

      int[] xs = new int[item];
      Arrays.setAll(xs, i -> i);

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
      System.out.println("---- item: " + item + " ----");
      for (int key = 0; key < item; key++) {
        map.justPut(key, key);
        map.justRemove(key);
        assertFalse(map.containsKey(key));
        assertTrue(map.isEmpty());
        assertEquals(defValue, map.getOrDefault(key, defValue));
      }
    }
  }
}