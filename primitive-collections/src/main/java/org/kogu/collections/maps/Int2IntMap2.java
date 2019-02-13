package org.kogu.collections.maps;

import static com.google.common.base.Preconditions.checkArgument;

class Int2IntMap2 implements IntIntMap {

  private int[] keys;
  private int[] values;
  private boolean[] used;
  private int size;
  private final float loadFactor;
  // We will resize a map once it reaches this size
  private int sizeThreshold;

  Int2IntMap2(int size, float loadFactor) {
    checkArgument(size > 0);
    checkArgument(0 < loadFactor && loadFactor < 1);

    // make array size power of 2
//    final int capacity = Tools.arraySize(size, loadFactor);
    final int capacity = Tools.tableSizeFor(size);

    keys = new int[capacity];
    values = new int[capacity];
    used = new boolean[capacity];
    this.loadFactor = 0.75f;
    this.sizeThreshold = (int) (capacity * loadFactor);
  }

  @Override
  public void put(int key, int value) {
    // resize check
    if (size == sizeThreshold) resize(2 * keys.length);

    int idx = putIndex(key);
    keys[idx] = key;
    values[idx] = value;
    if (!used[idx]) {
      size++;
      used[idx] = true;
    }
  }

  private void resize(int newCapacity) {
    sizeThreshold = (int) (loadFactor * newCapacity);

    // swap keys/values/used to new capacity
    int[] oldKeys = keys;
    int[] oldValues = values;
    boolean[] oldUsed = used;

    keys = new int[newCapacity];
    values = new int[newCapacity];
    used = new boolean[newCapacity];

    // reset `size` - it will be updated as part of `put`s
    size = 0;

    for (int i = 0; i < oldKeys.length; i++) {
      // DO NOT blindly `put` the k-v pairs - take only `used` ones!
      if (oldUsed[i])
        put(oldKeys[i], oldValues[i]);
    }
  }

  private int putIndex(int key) {
    int initial = Math.abs(Tools.phiMix(key) % keys.length);
    if (!used[initial]) return initial; // not used, virgin slot
    if (keys[initial] == key) return initial; // re-insertion

    // check next index
    // till it is unused or wraps to initial index
    int nextIdx = initial + 1 == keys.length ? 0 : initial + 1;

    // avoid wrapping past starting hashed index [aka, initial]
    // if slot == used, then check slot_key == query_key
    while (nextIdx != initial && used[nextIdx] && keys[nextIdx] != key)
      nextIdx = nextIdx + 1 == keys.length ? 0 : nextIdx + 1;

    return nextIdx != initial ? nextIdx : -1;
  }

  private int readIndex(int key) {
    int initial = Math.abs(Tools.phiMix(key) % keys.length);
    if (keys[initial] == key) return initial;

    // check next index
    // till it is == queried key; or, wraps to initial index
    int nextIdx = initial + 1 == keys.length ? 0 : initial + 1;
    while (keys[nextIdx] != key && nextIdx != initial)
      nextIdx = nextIdx + 1 == keys.length ? 0 : nextIdx + 1;

    return nextIdx != initial ? nextIdx : -1;
  }

  @Override
  public int getOrDefault(int key, int defaultValue) {
    int idx = readIndex(key);
    return idx == -1 ? defaultValue : values[idx];
  }

  @Override
  public void remove(int key) {}

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public boolean containsKey(int key) {
    return readIndex(key) != -1;
  }

  @Override
  public boolean containsValue(int value) {
    for (int i : values)
      if (value == i) return true;

    return false;
  }

  public static void main(String[] args) {
    int defValue = -1;
    Int2IntMap2 map = new Int2IntMap2(16, 0.75f);

    for (int key = 0; key < 10; key++) map.put(key, key);
    assert !map.isEmpty();
    assert map.size() == 10;

    for (int key = 0; key < 10; key++) map.put(key, key);
    assert !map.isEmpty();
    assert map.size() == 10;

    for (int key = 0; key < 10; key++) {
      int value = map.getOrDefault(key, defValue);
      assert value == key : "expected: " + key + ", got:" + value;
    }

    for (int key = 9; key >= 0; key--) {
      int value = map.getOrDefault(key, defValue);
      assert value == key : "expected: " + key + ", got:" + value;
    }

    for (int key = 0; key < 20; key++) map.put(key, 2 * key);
    assert !map.isEmpty();
    assert map.size() == 20;

    for (int key = 0; key < 20; key++) map.put(key, 2 * key);
    assert !map.isEmpty();
    assert map.size() == 20;

    for (int key = 0; key < 20; key++) {
      int value = map.getOrDefault(key, defValue);
      int expected = 2 * key;
      assert value == expected : "expected: " + expected + ", got:" + value;
    }

    for (int key = 19; key >= 0; key--) {
      int value = map.getOrDefault(key, defValue);
      int expected = 2 * key;
      assert value == expected : "expected: " + expected + ", got:" + value;
    }


  }
}
