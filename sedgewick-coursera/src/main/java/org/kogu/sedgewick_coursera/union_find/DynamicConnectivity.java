package org.kogu.sedgewick_coursera.union_find;

interface DynamicConnectivity {
  void union(int p, int q);
  boolean isConnected(int p, int q);
  int findComponentIdOf(int p);
  int count();
}
