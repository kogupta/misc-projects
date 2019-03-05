package org.kogu.sedgewick_coursera.tree;

import it.unimi.dsi.fastutil.ints.IntArrayList;

import java.util.Arrays;

import static org.kogu.sedgewick_coursera.CommonFunctions.assertionStatus;
import static org.kogu.sedgewick_coursera.CommonFunctions.createShuffledArray;

public final class BST {
  private final int defaultValue; //aka sentinel
  private Node root;

  public BST(int defaultValue) {
    this.defaultValue = defaultValue;
  }

  public void put(int key, int value) {
    root = put(root, key, value);
  }

  private Node put(Node x, int key, int value) {
    if (x == null) return Node.create(key, value);

    if (key < x.key) x.left = put(x.left, key, value);
    else if (key > x.key) x.right = put(x.right, key, value);
    else x.value = value;
    x.size = 1 + size(x.left) + size(x.right);

    return x;
  }

  public int get(int key) {
    Node x = root;
    while (x != null) {
      if (key > x.key) x = x.right;
      else if (key < x.key) x = x.left;
      else return x.value;
    }

    return defaultValue;
  }

  public int size() { return size(root);}

  private int size(Node x) {
    return x == null ? 0 : x.size;
  }

  public void deleteKey(int key) {

  }

  public boolean containsKey(int key) {
    Node x = root;
    while (x != null) {
      if (x.key < key) x = x.right;
      else if (x.key > key) x = x.left;
      else return true;
    }

    return false;
  }

  public int minKey() {
    isEmptyTree(root);

    Node x = root;
    while (x.left != null) x = x.left;

    return x.key;
  }

  private static void isEmptyTree(Node root) {
    if (root == null) throw new IllegalStateException("empty tree");
  }

  public int maxKey() {
    isEmptyTree(root);

    Node x = root;
    while (x.right != null) x = x.right;

    return x.key;
  }

  public IntArrayList inOrder() {
    IntArrayList queue = new IntArrayList();
    inOrder(root, queue);
    return queue;
  }

  private void inOrder(Node x, IntArrayList queue) {
//    System.out.println(x == null ? "- x -" : x.toString());
    if (x == null) return;

    inOrder(x.left, queue);
    queue.add(x.key);
    inOrder(x.right, queue);
  }

  private static final class Node {
    final int key;
    int value;
    Node left, right;
    int size;

    private Node(int key) {this.key = key;}

    static Node create(int key, int value) {
      Node node = new Node(key);
      node.value = value;
      node.size = 1;
      return node;
    }

    @Override
    public String toString() { return "[key: " + key + " ]";}
  }

  public static void main(String[] args) {
    assertionStatus();
    int sentinel = -1;
    int size = 10;
    test(sentinel, size);
  }

  private static void test(int sentinel, int size) {
    BST bst = new BST(sentinel);
    int[] xs = createShuffledArray(size);
    for (int i = 0; i < xs.length; i++) {
      int n = xs[i];
      assert !bst.containsKey(n);
      assert bst.get(n) == sentinel;
      bst.put(n, n);
      assert bst.containsKey(n);
      assert bst.get(n) == n;
      assert bst.size() == i + 1;
    }

    assert bst.maxKey() == size - 1;
    assert bst.minKey() == 0;

    Arrays.sort(xs);
    assert Arrays.equals(xs, bst.inOrder().toIntArray());
  }
}
