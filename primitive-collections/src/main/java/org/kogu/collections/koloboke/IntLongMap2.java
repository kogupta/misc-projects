package org.kogu.collections.koloboke;

import com.koloboke.compile.KolobokeMap;
import com.koloboke.function.IntLongConsumer;
import com.koloboke.function.IntLongToLongFunction;

import java.util.function.IntConsumer;
import java.util.function.IntToLongFunction;
import java.util.function.LongBinaryOperator;

@KolobokeMap
public abstract class IntLongMap2 {
  public abstract void justPut(int key, long value);

  public abstract long putIfAbsent(int key, long value);

  public abstract boolean justRemove(int key);

  public abstract int[] keys();

//  public abstract LongCollection values();

  public abstract boolean contains(int key);

  public abstract long get(int key);

  public abstract long getOrDefault(int key, long defaultValue);

  public abstract long compute(int key, IntLongToLongFunction remappingFunction);

  public abstract long computeIfAbsent(int key, IntToLongFunction mappingFunction);

  public abstract long computeIfPresent(int key, IntLongToLongFunction remappingFunction);

  public abstract long merge(int key, long value, LongBinaryOperator remappingFunction);

  public abstract void forEach(IntConsumer action);

  public abstract void forEach(IntLongConsumer action);


  public static IntLongMap2 withExpectedSize(int expectedSize) {
    return new KolobokeIntLongMap2(expectedSize);
  }
}
