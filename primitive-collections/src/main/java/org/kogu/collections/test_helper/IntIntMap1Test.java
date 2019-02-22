package org.kogu.collections.test_helper;

import org.kogu.collections.maps.IntIntMap;

public class IntIntMap1Test extends BaseIntIntMapTest {
  @Override
  public IntIntMap makeMap(int size, float fillFactor) {
    return IntIntMap.newInstance(size, fillFactor);
  }
}


