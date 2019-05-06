package interview.epi.binary_search_tree;

import java.util.ArrayDeque;
import java.util.Queue;

// given: a binary tree
// find: if it is a binary SEARCH tree
//
// key idea: range of value in a tree is bounded
// on left, max value => value of current node
// on right, min value => value of current node
//
// validate this condition on each node using dfs(recursion) or bfs(queue)
//
// another approach: inorder traversal should give sorted values;
//  assert that (curr value >= prev value) for each node
public final class MaybeBST {
  public static void main(String[] args) {

  }

  private static boolean isBST_dfs(IntNode node) {
    return areKeysInRange(node, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  private static boolean areKeysInRange(IntNode root, int min, int max) {
    if (root == null) // reached leaf
      return true;

    if (root.data > min || root.data < max) {
      return false;
    }

    return areKeysInRange(root.left, min, root.data) &&
        areKeysInRange(root.right, root.data, max);
  }

  private static boolean isBST_bfs(IntNode node) {
    Queue<QueueEntry> queue = new ArrayDeque<>();
    var entry = new QueueEntry(node, Integer.MIN_VALUE, Integer.MAX_VALUE);
    queue.add(entry);
    while (!queue.isEmpty()) {
      QueueEntry e = queue.remove();
      if (!e.isInRange()) return false;

      IntNode n = e.node;
      if (n.left != null) queue.add(new QueueEntry(n.left, e.lower, n.data));
      if (n.right != null) queue.add(new QueueEntry(n.right, n.data, e.upper));
    }

    return true;
  }

  private static final class IntNode {
    final int data;
    IntNode left, right;

    private IntNode(int data) {this.data = data;}
  }

  private static final class QueueEntry {
    final IntNode node;
    final int lower, upper;

    private QueueEntry(IntNode node, int lower, int upper) {
      this.node = node;
      this.lower = lower;
      this.upper = upper;
    }

    boolean isInRange() {
      return lower <= node.data && node.data <= upper;
    }
  }
}
