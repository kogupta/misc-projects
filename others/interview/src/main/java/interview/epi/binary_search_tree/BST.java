package interview.epi.binary_search_tree;

import org.jetbrains.annotations.Nullable;

final class BST<K extends Comparable<K>, V> {
  Node<K, V> root;

  BST(Node<K, V> root) {this.root = root;}

  public void put(K key, V value) {
    root = _put(root, key, value);
  }

  private Node<K, V> _put(Node<K, V> node, K key, V value) {
    if (node == null) return Node.of(key, value);

    int cmp = node.key.compareTo(key);
    if (cmp == 0)     node.value = value;
    else if (cmp > 0) node.left = _put(node.left, key, value);
    else              node.right = _put(node.right, key, value);

    return node;
  }

  @Nullable
  public V get(K key) {
    Node<K, V> node = find(root, key);
    return node == null ? null : node.value;
  }

  private Node<K, V> find(Node<K, V> node, K key) {
    if (node == null) return null;

    int cmp = node.key.compareTo(key);
    if (cmp == 0)     return node;
    else if (cmp > 0) return find(node.left, key);
    else              return find(node.right, key);
  }

  public K floor(K key) {
    Node<K, V> node = _floor(root, key);
    return node == null ? null : node.key;
  }

  private Node<K, V> _floor(Node<K, V> node, K key) {
    // largest key < param
    if (node == null) return null;

    int cmp = node.key.compareTo(key);
    if (cmp == 0) return node;
    if (cmp > 0) return _floor(node.left, key);

    // current key < param key
    // this is a CANDIDATE floor
    // search right -> if nothing found, then return current key
    Node<K, V> f = _floor(node.right, key);
    return f == null ? node : f;
  }

  public K ceiling(K key) {
    Node<K, V> node = _ceiling(root, key);
    return node == null ? null : node.key;
  }

  private Node<K, V> _ceiling(Node<K, V> node, K key) {
    // smallest key > param
    if (node == null) return null;

    int cmp = node.key.compareTo(key);
    if (cmp == 0) return node;
    if (cmp < 0) return _ceiling(node.right, key);

    // current key > param => maybe CANDIDATE ceiling
    // search left -> ceiling = coalesce(ceiling(left subtree), curr)
    Node<K, V> c = _ceiling(node.left, key);
    return c == null ? node : c;
  }

  static final class Node<K extends Comparable<K>, V> {
    final K key;
    V value;
    Node<K, V> left, right;

    private Node(K key) {this.key = key;}

    @Override
    public String toString() {
      return String.format("[%s => %s]", key, value);
    }

    static <K extends Comparable<K>, V> Node<K, V> of(K key, V value) {
      Node<K, V> node = new Node<>(key);
      node.value = value;
      return node;
    }
  }

}
