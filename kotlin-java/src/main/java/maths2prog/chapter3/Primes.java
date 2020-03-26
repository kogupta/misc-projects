package maths2prog.chapter3;

import maths2prog.Utils;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static maths2prog.Utils.time;

public class Primes {
  public static void main(String[] args) {
    // value(i) = 2i + 3
    // index(v) = (v - 3 ) / 2
//    Before we see how to sift, we observe the following sifting lemmas:
//    • The square of the smallest prime factor of a composite number c is less than or equal to c.
//    • Any composite number less than p^2 is sifted by (i.e., crossed out as a multiple of) a prime less than p.
//    • When sifting by p, start marking at p^2.
//    • If we want to sift numbers up to m, stop sifting when p^2> m.

    int[] sizes = {100, 1_000, 10_000, 100_000, 1_000_000, 10_000_000};
    for (int size : sizes) {
      System.out.printf("Size: %,3d%n", size);
      int[] primes = time(() -> primes(size).toArray());
      if (!validate(primes)) {
        System.out.println(display(primes));
        throw new AssertionError("non prime detected!");
      }

      // π(n) = number of primes < n
      long piN = Arrays.stream(primes)
          .filter(n -> n < size)
          .count();
      System.out.printf("(%d, %d)%n%n", size, piN);
    }
  }

  private static IntStream primes(int n) {
    boolean[] arr = new boolean[n];
    Arrays.fill(arr, true);
    int i = 0;
    // index_square = 2*i^2 + 6*i + 3
    int indexSquare = 3;
    while (indexSquare < n) {
      if (arr[i]) { // candidate is prime
        mark(arr, indexSquare, 2 * i + 3);
      }
      i++;
      indexSquare = 2 * i * i + 6 * i + 3;
    }
    return toPrimes(arr);
  }

  private static boolean validate(int[] xs) {
    for (int x : xs) {
      BigInteger n = BigInteger.valueOf(x);
      if (!n.isProbablePrime(10)) {
        System.out.println("Not prime: " + x);
        return false;
      }
    }

    return true;
  }

  private static IntStream toPrimes(boolean[] xs) {
    IntStream.Builder builder = IntStream.builder();
    for (int i = 0; i < xs.length; i++) {
      boolean b = xs[i];

      if (b) {
        int p = 2 * i + 3;
        builder.add(p);
      }
    }

    return builder.build();
  }

  private static void mark(boolean[] arr, int from, int step) {
//    System.out.println("factor: " + from + "\n" + display(arr));
    arr[from] = false;
    int curr = from;
    int to = arr.length;
    while (to - curr > step) {
      curr += step;
      arr[curr] = false;
    }

//    System.out.println(display(arr));
//    System.out.println();
  }

  private static String display(int[] xs) {
    StringJoiner joiner = new StringJoiner(", ");
    for (int x : xs) {
      String s = Integer.toString(x);
      joiner.add(s);
    }
    return joiner.toString();
  }
}
