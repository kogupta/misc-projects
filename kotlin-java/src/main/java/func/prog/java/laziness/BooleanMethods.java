package func.prog.java.laziness;

import java.util.function.Supplier;

public final class BooleanMethods {
  public static void main(String[] args) {
    System.out.println(getFirst() || getSecond());
    //noinspection Convert2MethodRef
    System.out.println(or(() -> getFirst(), () -> getSecond()));
  }

  public static boolean getFirst() { return true; }

  public static boolean getSecond() { throw new IllegalStateException(); }

  public static boolean or(Supplier<Boolean> a, Supplier<Boolean> b) {
    return a.get() || b.get();
  }

  public static boolean and(Supplier<Boolean> a, Supplier<Boolean> b) {
    return a.get() && b.get();
  }
}
