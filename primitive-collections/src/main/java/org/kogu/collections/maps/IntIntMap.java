package org.kogu.collections.maps;

public interface IntIntMap {
  void put(int key, int value);

  int getOrDefault(int key, int defaultValue);

  void remove(int key);

  int size();

  boolean isEmpty();

  boolean containsKey(int key);

  boolean containsValue(int value);

}
