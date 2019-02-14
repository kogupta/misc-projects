package org.kogu.collections.maps;

import java.util.function.IntBinaryOperator;

import static java.util.Objects.requireNonNull;

public interface IntIntMap {
  int getOrDefault(int key, int defaultValue);

  void justPut(int key, int value);

  void justRemove(int key);

  int size();
  boolean isEmpty();
  boolean containsKey(int key);
  boolean containsValue(int value);

  void clear();

//  void putAll(IntIntMap other);
//  void putAll(Map<Integer, Integer> other);

//  boolean removeIfPresent(int key, int value);
//  boolean replaceIfPresent(int key, int oldValue, int newValue);
//  boolean replaceIfPresent(int key, int value);

//  int computeIfAbsent(int key, IntUnaryOperator mappingFunction);
//  int computeIfPresent(int key, IntBinaryOperator mappingFunction);


  /**
   * If the specified key is not already associated with a value,
   * associates it with the given value.
   * Otherwise, replaces the associated value with the results of the given
   * remapping function.
   * <p>
   * Please note, if {@code key} is present, the {@code remappingFunction} is
   * applied as:
   * <p>
   * <pre> {@code
   * int newVal = remappingFunction.applyAsInt(existingMappedValue, intParameter);
   * justPut(key, newVal);
   * return newVal;
   * }</pre>
   * <p>
   * Be aware of the order of parameters to the {@code remappingFunction}.
   *
   * @param key               key with which the resulting value is to be associated
   * @param value             the value to be merged with the existing value
   *                          associated with the key or, if no existing value
   *                          is associated with the key, to be associated with the key
   * @param remappingFunction the function to recompute a value <b>if present</b>
   * @return the new value associated with the specified key, or null if no
   * value is associated with the key
   */
  default int merge(int key, int value, IntBinaryOperator remappingFunction) {
    requireNonNull(remappingFunction);

    if (containsKey(key)) {
      int existingVal = getOrDefault(key, -1);
      int newVal = remappingFunction.applyAsInt(existingVal, value);
      justPut(key, newVal);
      return newVal;
    } else {
      justPut(key, value);
      return value;
    }
  }

}
