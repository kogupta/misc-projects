package interview.epi.binary_search_tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static interview.epi.array.SortFunctions.assertionStatus;
import static interview.epi.binary_search_tree.FirstGreaterThanK.sampleBST;

final class KLargestElements {
  public static void main(String[] args) {
    assertionStatus();

    var bst = sampleBST();
    var got = findKLargest(bst.root, 3);
    var expected = Arrays.asList(53, 47, 43);

    assert Objects.equals(expected, got) :
        "Expected: " + expected + ", got: " + got;
  }

  private static <K extends Comparable<K>, V> List<K> findKLargest(BST.Node<K, V> root, int k) {
    List<K> result = new ArrayList<>(k);
    helper(root, k, result);
    return result;
  }

  private static <K extends Comparable<K>, V> void helper(BST.Node<K, V> node, int k, List<K> acc) {
    // reverse inorder traversal
    if (node != null && acc.size() < k) {
      helper(node.right, k, acc);
      if (acc.size() < k) {
        acc.add(node.key);
        helper(node.left, k, acc);
      }
    }
  }
}
