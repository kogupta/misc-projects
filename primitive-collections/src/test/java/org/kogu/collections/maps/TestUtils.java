package org.kogu.collections.maps;

import java.util.Random;

public enum TestUtils {
  ;

  public static class KeyGenerator {
    public static int s_mapSize;
    public static int[] s_keys;

    public static int[] getKeys(final int mapSize) {
      if (mapSize == s_mapSize)
        return s_keys;
      s_mapSize = mapSize;
      s_keys = null; //should be done separately so we don't keep 2 arrays in memory
      s_keys = new int[mapSize];
      final Random r = new Random(1234);
      for (int i = 0; i < mapSize; ++i)
        s_keys[i] = r.nextInt();
      return s_keys;
    }
  }

}
