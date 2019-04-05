package org.kogu.sedgewick_coursera;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.Consumer;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.System.out;

public final class Iterables {
  private Iterables() {}

  @SafeVarargs
  public static <T> Iterable<T> concat(Iterable<T>... iterables) {
    if (iterables == null || iterables.length == 0) {
      return Collections.emptyList();
    }

    return () -> new IteratorConcat<>(iterables);
  }

  private static final class IteratorConcat<T> implements Iterator<T> {
    private final Iterable<T>[] iterables;
    private final int maxIdx;
    private int idx;
    private Iterator<T> curr;

    IteratorConcat(Iterable<T>[] iterables) {
      this.iterables = iterables;
      idx = 0;
      curr = iterables[idx].iterator();
      maxIdx = iterables.length - 1;
    }

    @Override
    public boolean hasNext() {
      if (curr.hasNext()) return true;

      while (!curr.hasNext() && idx < maxIdx) {
        idx++;
        curr = iterables[idx].iterator();
      }

      return curr.hasNext();
    }

    @Override
    public T next() {
      return curr.next();
    }
  }

  public static void main(String... args) {
    _assertionStatus();

    ArrayList<Integer> as = newArrayList(1, 2, 3);
    ArrayList<Integer> bs = newArrayList(4, 5);
    ArrayList<Integer> cs = newArrayList(6);
    ArrayList<Integer> ds = newArrayList();

    Consumer<Integer> action = n -> System.out.print(n + " ");

    System.out.println("expect: nada");
    System.out.print("got   : ");
    Iterables.concat(ds).forEach(action); System.out.println();

    System.out.println("expect: 1 2 3 4 5 6");
    System.out.print("got   : ");
    Iterables.concat(as, bs, cs).forEach(action); System.out.println();

    System.out.println("expect: 1 2 3 4 5 6");
    System.out.print("got   : ");
    Iterables.concat(as, bs, cs, ds).forEach(action); System.out.println();

    System.out.println("expect: 1 2 3 4 5 6");
    System.out.print("got   : ");
    Iterables.concat(ds, as, bs, cs).forEach(action); System.out.println();

    System.out.println("expect: 1 2 3 4 5 6");
    System.out.print("got   : ");
    Iterables.concat(as, ds, bs, ds, cs, ds).forEach(action); System.out.println();
  }

  //<editor-fold desc="assertion status">
  private static void _assertionStatus() {
    String status = Iterables.class.desiredAssertionStatus() ? "enabled" : "disabled";
    out.println("Assertion: " + status);
    out.println("====================");
  }
//</editor-fold>
}
