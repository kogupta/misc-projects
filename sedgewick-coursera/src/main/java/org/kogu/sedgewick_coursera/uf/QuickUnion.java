package org.kogu.sedgewick_coursera.uf;

import java.util.Arrays;

class QuickUnion implements DynamicConnectivity {
  private final int[] id;
  private int size;

  QuickUnion(int N) {
    assert N > 0 : "require: universe of items > 0";
    id = new int[N];
    Arrays.setAll(id, i -> i);
    size = N;
  }

  @Override
  public void union(int p, int q) {
    int pRoot = rootOf(p);
    int qRoot = rootOf(q);

    if (pRoot != qRoot) {
      id[pRoot] = qRoot;
      size--;
    }
  }

  @Override
  public boolean isConnected(int p, int q) {
    return rootOf(p) == rootOf(q);
  }

  private int rootOf(int idx) {
    if (id[idx] == idx) return idx;

    return rootOf(id[idx]);
  }

  @Override
  public int findComponentIdOf(int p) {
    return id[p];
  }

  @Override
  public int count() {
    return size;
  }

  public static void main(String[] args) {
    QuickUnion qu = new QuickUnion(10);
    qu.union(4, 3);
    qu.union(3, 8);
    qu.union(6, 5);
    qu.union(9, 4);
    qu.union(2, 1);

    assert qu.isConnected(8, 9);
    int[] e0 = {0, 1, 1, 8, 3, 5, 5, 7, 8, 8};
    assert Arrays.equals(qu.id, e0);
    assert qu.size == 5;
    assert !qu.isConnected(5, 4);

    qu.union(5, 0);
    qu.union(7, 2);

    assert qu.count() == 3;
    int[] e1 = {0, 1, 1, 8, 3, 0, 5, 1, 8, 8};
    assert Arrays.equals(qu.id, e1);
  }
}
