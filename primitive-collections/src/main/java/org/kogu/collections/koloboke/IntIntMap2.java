package org.kogu.collections.koloboke;

//@KolobokeMap
public abstract class IntIntMap2 {
  public abstract int put(int key, int value);

  public abstract boolean justRemove(int key);

  public abstract boolean contains(int key);

  public abstract int getOrDefault(int key, int defaultValue);

  public static IntIntMap2 withExpectedSize(int expectedSize) {
    return new KolobokeIntIntMap2(expectedSize);
  }
}
