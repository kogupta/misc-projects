package org.kogu.sedgewick_coursera.union_find;

import java.util.Arrays;

class QuickFind implements DynamicConnectivity {
  private final int[] id;
  private int size;

  QuickFind(int N) {
    assert N > 0 : "require: universe of items > 0";
    id = new int[N];
    Arrays.setAll(id, i -> i);
    size = N;
  }

  @Override
  public void union(int p, int q) {
    int pid = id[p];
    int qid = id[q];

    if (pid == qid) return;

    for (int idx = 0; idx < id.length; idx++) {
      if (id[idx] == pid) {
        id[idx] = qid;
      }
    }
    size--;
  }

  @Override
  public boolean isConnected(int p, int q) {
    return id[p] == id[q];
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
    QuickFind qf = new QuickFind(10);
    qf.union(4, 3);
    qf.union(3, 8);
    qf.union(6, 5);
    qf.union(9, 4);
    qf.union(2, 1);

    assert qf.isConnected(8, 9);

    qf.union(5, 0);
    qf.union(7, 2);

    assert qf.count() == 3;
    int[] expected = {0, 1, 1, 8, 8, 0, 0, 1, 8, 8};
    assert Arrays.equals(qf.id, expected);
  }
}
