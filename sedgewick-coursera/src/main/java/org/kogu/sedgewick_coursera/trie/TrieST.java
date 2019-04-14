package org.kogu.sedgewick_coursera.trie;

import java.util.Optional;

public final class TrieST<V> {
  private static final int R = 26; // # of characters in alphabet/set

  private final Node<V> root;

  public TrieST() {root = new Node<>();}

  public void put(String key, V value) {
    _put(root, key, value, 0);
  }

  private Node _put(Node node, String key, V value, int depth) {
    if (node == null) node = new Node();
    if (depth == key.length()) {
      node.value = value;
      return node;
    }

    int idx = key.charAt(depth) - 'a';
    node.next[idx] = _put(node.next[idx], key, value, depth + 1);
    return node;
  }

  public Optional<V> get(String key) {
    Node<V> node = _getNode(root, key, 0);
    return node == null ? Optional.empty() : Optional.of(node.value);
  }

  // this is PREFERABLE
  // reusable with PREFIX MATCH queries
  // version which returns value ie `_get` is not reusable
  private Node<V> _getNode(Node<V> node, String key, int depth) {
    if (node == null) return null;
    if (depth == key.length()) return node;

    int idx = key.charAt(depth) - 'a';
    return _getNode(node.next[idx], key, depth + 1);
  }

  private V _get(Node<V> node, String key, int depth) {
    if (node == null) return null;
    if (depth == key.length()) return node.value;

    int idx = key.charAt(depth) - 'a';
    return _get(node.next[idx], key, depth + 1);
  }

  public boolean contains(String key) {
    return _getNode(root, key, 0) != null;
  }

  public int size() {
    return _size(root);
  }

  // avoid!!
  private int _size(Node<V> node) {
    if (node == null) return 0;

    int size = 0;
    if (node.value != null) size++;
    for (Node<V> next : node.next)
      size += _size(next);

    return size;
  }

  private static final class Node<V> {
    V value;
    final Node<V>[] next = new Node[R];
  }
}
