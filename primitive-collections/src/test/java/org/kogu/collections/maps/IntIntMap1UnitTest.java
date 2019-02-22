package org.kogu.collections.maps;

public class IntIntMap1UnitTest extends BaseIntIntMapUnitTest {
  @Override
  protected IntIntMap makeMap(int size, float fillFactor) {
    return new IntIntMapOpenHashMapImpl(size, fillFactor);
  }
}
