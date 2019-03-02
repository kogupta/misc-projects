package org.kogu.sedgewick_coursera.uf;

import java.util.Arrays;

class WQuickUnion implements DynamicConnectivity {
  private final int[] id;
  private final int[] sz;
  private int size;

  WQuickUnion(int N) {
    assert N > 0 : "require: universe of items > 0";

    id = new int[N];
    Arrays.setAll(id, i -> i);

    sz = new int[N];
    Arrays.fill(sz, 1);

    size = N;
  }

  @Override
  public void union(int p, int q) {
    int pRoot = rootOf(p);
    int qRoot = rootOf(q);

    if (pRoot == qRoot) return;

    // Make smaller root point to larger one.
    if (sz[pRoot] < sz[qRoot]) {
      id[pRoot] = qRoot;
      sz[qRoot] += sz[pRoot];
    } else {
      id[qRoot] = pRoot;
      sz[pRoot] += sz[qRoot];
    }

    size--;
  }

  @Override
  public boolean isConnected(int p, int q) {
    return rootOf(p) == rootOf(q);
  }

  private int rootOf(int idx) {
    while (id[idx] != idx) {
      idx = id[idx];
    }

    return idx;
  }

  private int _rootOf(int idx) {
    if (id[idx] == idx) return idx;

    return _rootOf(id[idx]);
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
    WQuickUnion qu = new WQuickUnion(10);
    qu.union(4, 3);
    qu.union(3, 8);
    qu.union(6, 5);
    qu.union(9, 4);
    qu.union(2, 1);

    assert qu.isConnected(8, 9);
    int[] e0 = {0, 2, 2, 4, 4, 6, 6, 7, 4, 4};
    assert Arrays.equals(qu.id, e0);
    assert qu.size == 5;
    assert !qu.isConnected(5, 4);

    qu.union(5, 0);
    qu.union(7, 2);

    assert qu.count() == 3;
    int[] e1 = {6, 2, 2, 4, 4, 6, 6, 2, 4, 4};
    assert Arrays.equals(qu.id, e1);
  }
}
