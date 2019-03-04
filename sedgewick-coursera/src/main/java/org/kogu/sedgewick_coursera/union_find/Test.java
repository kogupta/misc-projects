package org.kogu.sedgewick_coursera.union_find;

public class Test {
  public static void check(DynamicConnectivity dc) {
    dc.union(4, 3);
    dc.union(3, 8);
    dc.union(6, 5);
    dc.union(9, 4);
    dc.union(2, 1);

    assert dc.isConnected(8, 9);

    dc.union(5, 0);
    dc.union(7, 2);

    assert dc.count() == 3;
  }

  public static void main(String[] args) {
    check(new QuickFind(10));
  }
}
