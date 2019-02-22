package org.kogu.collections.test_helper;

import org.kogu.collections.maps.IntIntMap;

public abstract class BaseIntIntMapTest implements ITestSet {
  abstract public IntIntMap makeMap(final int size, final float fillFactor);

  @Override
  public IMapTest getTest() {
    return new IntIntMapGetTest();
  }

  @Override
  public IMapTest putTest() {
    return new IntIntMapPutTest();
  }

  @Override
  public IMapTest removeTest() {
    return new IntIntMapRemoveTest();
  }

  private class IntIntMapGetTest extends AbstractPrimPrimGetTest {
    private IntIntMap m_map;

    @Override
    public void setup(final int[] keys, final float fillFactor, int oneFailOutOf) {
      super.setup(keys, fillFactor, oneFailOutOf);
      m_map = makeMap(keys.length, fillFactor);
      for (int key : keys) m_map.justPut(key + (key % oneFailOutOf == 0 ? 1 : 0), key);
    }

    @Override
    public int test() {
      int res = 0;
      for (int i = 0; i < m_keys.length; ++i)
        res = res ^ m_map.getOrDefault(m_keys[i], -1);
      return res;
    }
  }

  private class IntIntMapPutTest extends AbstractPrimPrimPutTest {
    @Override
    public int test() {
      final IntIntMap m_map = makeMap(m_keys.length, m_fillFactor);
      for (int i = 0; i < m_keys.length; ++i)
        m_map.justPut(m_keys[i], m_keys[i]);
      for (int i = 0; i < m_keys.length; ++i)
        m_map.justPut(m_keys[i], m_keys[i]);
      return m_map.size();
    }
  }

  private class IntIntMapRemoveTest extends AbstractPrimPrimPutTest {
    @Override
    public int test() {
      final IntIntMap m_map = makeMap(m_keys.length / 2 + 1, m_fillFactor);
      int add = 0, remove = 0;
      while (add < m_keys.length) {
        m_map.justPut(m_keys[add], m_keys[add]);
        ++add;
        m_map.justPut(m_keys[add], m_keys[add]);
        ++add;
        m_map.justRemove(m_keys[remove++]);
      }
      return m_map.size();
    }
  }

}
