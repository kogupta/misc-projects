package org.kogu.collections.maps;

import java.util.Arrays;

import static com.google.common.base.Preconditions.checkArgument;

class IntIntMapOpenHashMapImpl implements IntIntMap {

  // keys and values are interleaved
  // key: even locations
  // value: key + 1, odd locations
  private int[] data;
  private boolean[] used;
  private int size;
  private final float loadFactor;
  // We will resize a map once it reaches this size
  private int sizeThreshold;

  IntIntMapOpenHashMapImpl(int size, float loadFactor) {
    checkArgument(size > 0);
    checkArgument(0 < loadFactor && loadFactor < 1);

    // make array size power of 2
//    final int capacity = Tools.arraySize(size, loadFactor);
    final int capacity = Tools.tableSizeFor(size);

    data = new int[2 * capacity];
    used = new boolean[capacity];
    this.loadFactor = loadFactor;
    this.sizeThreshold = (int) (capacity * loadFactor);
  }

  @Override
  public void justPut(int key, int value) {
    // resize check
    if (size == sizeThreshold) resize(2 * data.length);

    int idx = putIndex(key);
    data[idx] = key;
    data[idx + 1] = value;
    if (!used[idx / 2]) {
      size++;
      used[idx / 2] = true;
    }

//    Tools.debug(data, used);
//    System.out.println("size:" + size);
  }

  private void resize(int newCapacity) {
    sizeThreshold = (int) (loadFactor * newCapacity);

    // swap keys/values/used to new capacity
    int[] oldData = data;
    boolean[] oldUsed = used;

    data = new int[2 * newCapacity];
    used = new boolean[newCapacity];

    // reset `size` - it will be updated as part of `put`s
    size = 0;

    for (int i = 0; i < oldUsed.length; i++) {
      // DO NOT blindly `put` the k-v pairs - take only `used` ones!
      if (oldUsed[i]) {
        int keyIdx = 2 * i;
        justPut(oldData[keyIdx], oldData[keyIdx + 1]);
      }
    }
  }

  private int putIndex(int key) {
    int initial = arrayIndex(key);
    int keyIdx = 2 * initial;
    if (!used[initial]) return keyIdx; // not used, virgin slot
    if (data[keyIdx] == key) return keyIdx; // re-insertion

    // check next index
    // till it is unused or wraps to initial index
    int nextIdx = nextIndex(keyIdx);

    // avoid wrapping past starting hashed index [aka, keyIdx]
    // if slot == used, then check slot_key == query_key
    while (nextIdx != keyIdx && used[nextIdx / 2] && data[nextIdx] != key)
      nextIdx = nextIndex(nextIdx);

    return nextIdx != keyIdx ? nextIdx : -1;
  }

  private int nextIndex(int keyIdx) {
    return keyIdx + 2 == data.length ? 0 : keyIdx + 2;
  }

  private int arrayIndex(int key) {
    int a = Tools.phiMix(key) % used.length;
    return (a < 0) ? -a : a;
  }

  private int readIndex(int key) {
    int initial = arrayIndex(key);
    int keyIdx = 2 * initial;

    if (used[initial] && data[keyIdx] == key) return keyIdx;

    // check next index
    // till it is == queried key; or, wraps to initial index
    int nextIdx = nextIndex(keyIdx);
    while (data[nextIdx] != key && used[nextIdx / 2] && nextIdx != keyIdx)
      nextIdx = nextIndex(nextIdx);

    return nextIdx != keyIdx && used[nextIdx / 2] ? nextIdx : -1;
  }

  @Override
  public int getOrDefault(int key, int defaultValue) {
    int idx = readIndex(key);
    return idx == -1 ? defaultValue : data[idx + 1];
  }

  @Override
  public void justRemove(int key) {
    int idx = readIndex(key);
    if (idx == -1) return;

    data[idx] = data[idx + 1] = 0;
    used[idx / 2] = false;
    size--;

    // shift left
    for (int i = nextIndex(idx); used[i / 2]; i = nextIndex(i)) {
      int oldKey = data[i];
      int oldValue = data[i + 1];
      used[i / 2] = false;
      size--;
      data[i] = data[i + 1] = 0;

      justPut(oldKey, oldValue);
    }

  }

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
    for (int i = 0; i < used.length; i++) {
      boolean isUsed = used[i];
      if (isUsed && data[2 * i + 1] == value) {
        return true;
      }
    }

    return false;
  }

  @Override
  public void clear() {
    if (size == 0) return;

    size = 0;
    Arrays.fill(data, 0);
    Arrays.fill(used, false);
  }
}
