package org.kogu.trie;

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
    return Optional.ofNullable(_get(root, key, 0));
  }

  private V _get(Node<V> node, String key, int depth) {
    if (node == null) return null;
    if (depth == key.length()) return (V) node.value;

    int idx = key.charAt(depth) - 'a';
    return _get(node.next[idx], key, depth + 1);
  }

  public boolean contains(String key) {
    return _get(root, key, 0) != null;
  }

  private static final class Node<V> {
    V value;
    final Node<V>[] next = new Node[R];
  }
}
