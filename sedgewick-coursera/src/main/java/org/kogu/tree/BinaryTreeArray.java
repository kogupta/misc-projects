package org.kogu.tree;

// http://mishadoff.com/blog/dfs-on-binary-tree-array/
public class BinaryTreeArray<T> {
  // tree on static array
  // lets keep index 0 as empty!
  // node at idx n -> children at 2n, 2n+1, parent at n/2




  private static final class BinaryNode<T> {
    T data;
    BinaryNode<T> left;
    BinaryNode<T> right;
  }

}
