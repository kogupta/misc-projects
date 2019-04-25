package interview.epi;

import java.util.Arrays;

// 2nd problem in programming pearls
// McIlroy "hand waving" :D -> test with 2 hands
//
// intuition behind the problem:
// right/left rotation involves "starting" the array from some index at right/left
// [1,2,3,4,5] rotate right 2 => [4, 5, 1, 2, 3]
// array: [.....| ...] => [a | b]
//   [ rev(a) | b ]
//   [ rev(a) | rev(b) ]
//   rev( [ rev(a) | rev(b) ] ) => [ b | a ]
public final class ArrayRotations {
  public static void main(String[] args) {

    test(7, 3);
    test(5, 2);
  }

  private static void test(int len, int rotateBy) {
    assert len > rotateBy;

    System.out.println("array length: " + len + ", rotate by: " + rotateBy);
    System.out.println("left: ");
    int[] xs = createArray(len);
    rotateLeft(xs, rotateBy);
    System.out.println(Arrays.toString(xs));
    System.out.println();

    System.out.println("right: ");
    xs = createArray(len);
    rotateLeft(xs, len - rotateBy);
    System.out.println(Arrays.toString(xs));
    System.out.println();
  }

  private static void rotateLeft(int[] arr, int rotateBy) {
    reverse(arr, 0, rotateBy - 1);
    System.out.println(Arrays.toString(arr));

    reverse(arr, rotateBy, arr.length - 1);
    System.out.println(Arrays.toString(arr));

    reverse(arr, 0, arr.length - 1);
  }

  private static void reverse(int[] arr, int from, int to) {
    for (int i = from, j = to; i < j; i++, j--) {
      swap(arr, i, j);
    }
  }

  private static void swap(int[] arr, int i, int j) {
    int tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }

  private static int[] createArray(int elements) {
    int[] xs = new int[elements];
    Arrays.setAll(xs, i -> i + 1);
    return xs;
  }
}
