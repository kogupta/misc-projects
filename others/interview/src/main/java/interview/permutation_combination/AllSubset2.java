package interview.permutation_combination;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static java.lang.Integer.toBinaryString;

public final class AllSubset2 {
  public static void main(String[] args) {
    justPrint(Set.of());
    justPrint(Set.of(1));
    justPrint(Set.of(1, 2));
    justPrint(Set.of(1, 2, 3));
    justPrint(Set.of(1, 2, 3, 4));
    justPrint(Set.of(1, 2, 3, 4, 5));
    display(allSubsets(Set.of()));
    display(allSubsets(Set.of(1)));
    display(allSubsets(Set.of(1, 2)));
    display(allSubsets(Set.of(1, 2, 3)));
    display(allSubsets(Set.of(1, 2, 3, 4)));
    display(allSubsets(Set.of(1, 2, 3, 4, 5)));
  }

  private static <T> void display(Iterator<Set<T>> xss) {
    xss.forEachRemaining(System.out::println);
//    List<String> s = xss.stream()
//        .sorted(Comparator.comparingInt(Set::size))
//        .map(Object::toString)
//        .collect(Collectors.toList());
//    System.out.println(xss.size() + ": " + s);
    System.out.println();
  }

  public static <T> Iterator<Set<T>> allSubsets(Set<T> input) {
    return new PowerSet<>(input);
  }

  public static <T> void justPrint(Set<T> input) {
//    int n = powerOf2(input.size());
    int n = 1 << input.size();

    T[] ts = input.toArray(value -> (T[])new Object[value]);
    Arrays.sort(ts);
    System.out.println("Elements: " + Arrays.toString(ts));
    for (int i = 0; i < n; i++) {
      System.out.print("state: " + toBinaryString(i) + " => ");

      for (int idx = 0; idx < ts.length; idx++) {
        // is bit set?
        if ((i & (1 << idx)) > 0) {
          System.out.print(ts[idx] + ", ");
        }
      }
      System.out.println();
    }

    System.out.println("----------------");
  }

  private static final class PowerSet<T> implements Iterator<Set<T>> {
    private final T[] elements;
    private final int size;
    private int position;

    private PowerSet(Set<T> input) {
      elements = input.toArray(n -> (T[])new Object[n]);
      size = 1 << input.size();
    }

    @Override
    public boolean hasNext() {
      return position < size;
    }

    @Override
    public Set<T> next() {
      System.out.println("Current state: " + toBinaryString(position));
      Set<T> result = new HashSet<>();
      for (int i = 0; i < elements.length; i++) {
        if ((position & (1 << i)) > 0) {
          T element = elements[i];
          result.add(element);
        }
      }

      position++;
      return result;
    }
  }
}
