package interview.epi.binary_search_tree;

import org.jetbrains.annotations.NotNull;

import java.util.StringJoiner;

import static interview.epi.array.SortFunctions.assertionStatus;
import static interview.epi.binary_search_tree.BST.Node.of;

// find first greater than k
// similar to ceiling
// find first key > param - "first" as decided by inorder traversal
public final class FirstGreaterThanK {
  public static void main(String[] args) {
    assertionStatus();

    var bst = sampleBST();

    assert bst.floor(7) == 7;
    assert bst.floor(12) == 11;
    assert bst.floor(1) == null;

    assert bst.ceiling(7) == 7;
    assert bst.ceiling(36) == 37;
    assert bst.ceiling(24) == 29;
    assert bst.ceiling(54) == null;

    assert findFirstGreaterThanK(bst.root, 23).key == 29;

    bst = sampleBST2();
    inorder(bst);

    BST.Node<Integer, String> firstEqualsK = findFirstEqualsK(bst.root, 108);
    assert firstEqualsK.key == 108;
    assert firstEqualsK.value.equals("F");
  }

  private static <K extends Comparable<K>, V> void inorder(BST<K, V> bst) {
    StringJoiner joiner = new StringJoiner(", ");
    inorder(bst.root, joiner);
    System.out.println(joiner.toString());
  }

  private static <V, K extends Comparable<K>> void inorder(BST.Node<K, V> node, StringJoiner joiner) {
    if (node.left != null) inorder(node.left, joiner);
    joiner.add(node.toString());
    if (node.right != null) inorder(node.right, joiner);
  }


  @NotNull
  static BST<Integer, String> sampleBST2() {
    BST<Integer, String> bst;
    bst = new BST<>(of(108, "A"));

    bst.put(108, "B");
    bst.put(285, "G");

    bst.put(-10, "C");
    bst.put(108, "F");
    bst.put(243, "H");
    bst.put(285, "I");

    bst.put(-14, "D");
    bst.put(2, "E");
    bst.put(401, "J");
    return bst;
  }

  @NotNull
  // fig 15.1, page 255
  static BST<Integer, String> sampleBST() {
    var bst = new BST<>(of(19, "A"));

    bst.put(7, "B");
    bst.put(43, "I");

    bst.put(3, "C");
    bst.put(11, "F");
    bst.put(23, "J");
    bst.put(47, "O");

    bst.put(2, "D");
    bst.put(5, "E");
    bst.put(17, "G");
    bst.put(37, "K");
    bst.put(53, "P");

    bst.put(13, "H");
    bst.put(29, "L");
    bst.put(41, "N");

    bst.put(31, "M");
    return bst;
  }



  private static BST.Node<Integer, String> findFirstGreaterThanK(BST.Node<Integer, String> tree, int k) {
    BST.Node<Integer, String> subTree = tree, result = null;
    while (subTree != null) {
      if (subTree.key <= k) {
        subTree = subTree.right;
      } else {
        result = subTree;
        subTree = subTree.left;
      }
    }

    return result;
  }

  private static BST.Node<Integer, String> findFirstEqualsK(BST.Node<Integer, String> tree, int k) {
    BST.Node<Integer, String> subTree = tree, result = null;
    while (subTree != null) {
      if (subTree.key == k) {
        result = subTree;
        subTree = subTree.left;
      } else if (subTree.key < k) {
        subTree = subTree.right;
      } else {
        subTree = subTree.left;
      }
    }

    return result;
  }

}
