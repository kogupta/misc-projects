package maths2prog.chapter2;

public class Main {
  public static void main(String[] args) {
  }

  //region Egyptian multiplication
  private static int multiply0(int n, int a) {
    assert n > 0; assert a > 0;

    if (n == 1) return a;
    return multiply0(n - 1, a) + a;
  }

  // 41 * 59 = (1 + 8 + 32) * 59 = 1* 59 + 8 * 59 + 32 * 59
  // invoked floor(lg n) times
  // addition: [pop_count(n) - 1] times
  // if n is of form [2^n - 1], then no recursion, involves less steps than above
  private static int multiply1(int n, int a) {
    assert n > 0; assert a > 0;

    if (n == 1) return a;
    int result = multiply1(half(n), a + a);
    if (isOdd(n)) result += a;
    return result;
  }

  private static int multiply2(int n, int a) {
    assert n > 0; assert a > 0;

    if (n == 1) return a;
    return multiply_acc3(a, n - 1, a); // worst case when n is power of 2
  }

  private static int multiply3(int n, int a) {
    assert n > 0; assert a > 0;

    while (isEven(n)) {
      n = half(n);
      a = a + a;
    }

    if (n == 1) return a;
    return multiply_acc3(a, n - 1, a);
  }

  private static int multiply4(int n, int a) {
    assert n > 0; assert a > 0;

    while (isEven(n)) {
      n = half(n);
      a = a + a;
    }

    if (n == 1) return a;

    // n -> odd
    // n - 1 -> even
    return multiply_acc3(a, half(n - 1), a + a);
  }

  //endregion

  //region multiply-accumulate helper function, tail recursive to iterative
  // multiply n * a
  // n odd => n = 2k+1
  //          n * a = (2k + 1) * a = a + (k * 2a)
  //                                 ^ goes to accumulator
  // n even => n * a = 2k * a = k * 2a
  private static int multiply_acc0(int acc, int n, int a) {
    assert n > 0; assert a > 0;

    if (n == 1) return acc + a;
    if (isOdd(n)) return multiply_acc0(acc + a, half(n), a + a);
    else return multiply_acc0(acc, half(n), a + a);
  }

  private static int multiply_acc1(int acc, int n, int a) {
    assert n > 0; assert a > 0;

    if (n == 1) return acc + a;
    if (isOdd(n)) acc += a;
    return multiply_acc1(acc, half(n), a + a);
  }

  private static int multiply_acc2(int acc, int n, int a) {
    assert n > 0; assert a > 0;

    // n is rarely 1 + mutation iff n is odd
    if (isOdd(n)) {
      acc += a;
      if (n == 1) return acc;
    }
    return multiply_acc2(acc, half(n), a + a);
  }

  // iterative version
  private static int multiply_acc3(int acc, int n, int a) {
    assert n > 0; assert a > 0;

    while (true) {
      // n is rarely 1 + mutation iff n is odd
      if (isOdd(n)) {
        acc += a;
        if (n == 1) return acc;
      }
      a = a + a;
      n = half(n);
    }
  }
  //endregion

  //region helpers
  private static boolean isOdd(int n) { return (n & 1) == 1; }

  private static boolean isEven(int n) { return (n & 1) == 0; }

  private static int half(int n) { return n / 2; }
  //endregion
}
