package func.prog.java;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public final class PrimeSieve {
  public static boolean isPrime(int n) {
    return false;
  }

  public static IntStream primesTill(int n) {
    if (n <= 0) { throw new IllegalArgumentException("`n` is negative!"); }

    boolean[] isPrime = new boolean[n + 1];
    // initially assume all are prime
    for (int p = 2; p < isPrime.length; p++)
      isPrime[p] = true;

    for (int factor = 2; factor * factor <= n; factor++) {
      if (!isPrime[factor]) {continue;}

      int p = factor;
      int idx;
      while ((idx = factor * p) <= n) {
        isPrime[idx] = false;
        p++;
      }
    }

    IntStream.Builder builder = IntStream.builder();
    for (int i = 0; i < isPrime.length; i++) {
      if (isPrime[i]) { builder.add(i); }
    }

    return builder.build();
  }

  public static void main(String[] args) {
    IntConsumer print = n -> System.out.println(n);
    primesTill(10).forEach(print);
    primesTill(100).limit(10).forEach(print);
  }
}
