package interview.permutation_combination;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newHashSet;

public final class AllSubset {
  public static void main(String[] args) {
    display(allSubsets(Set.of()));
    display(allSubsets(Set.of(1)));
    display(allSubsets(Set.of(1, 2)));
    display(allSubsets(Set.of(1, 2, 3)));
    display(allSubsets(Set.of(1, 2, 3, 4)));
    display(allSubsets(Set.of(1, 2, 3, 4, 5)));
  }

  private static <T> void display(Set<Set<T>> xss) {
    List<String> s = xss.stream()
        .sorted(Comparator.comparingInt(Set::size))
        .map(Object::toString)
        .collect(Collectors.toList());
    System.out.println(xss.size() + ": " + s);
    System.out.println();
  }

  public static <T> Set<Set<T>> allSubsets(Set<T> input) {
    if (input == null || input.isEmpty()) { return newHashSet(newHashSet()); }

    T head = input.iterator().next();
    if (input.size() == 1) {
      return newHashSet(newHashSet(), newHashSet(head));
    }

    Set<T> rest = new LinkedHashSet<>(input.size());
    rest.addAll(input);
    rest.remove(head);
    Set<Set<T>> withoutHead = allSubsets(rest);
    Set<Set<T>> withHead = addElementTo2(withoutHead, head);

    Set<Set<T>> result = new HashSet<>();
    result.addAll(withoutHead);
    result.addAll(withHead);
    return result;
  }

  private static <T> Set<T> addElementTo(Set<T> xs, T x) {
    Set<T> result = new HashSet<>(xs.size() + 1);
    result.addAll(xs);
    result.add(x);
    return result;
  }

  private static <T> Set<Set<T>> addElementTo2(Set<Set<T>> xss, T x) {
    return xss.stream()
        .map(xs -> addElementTo(xs, x))
        .collect(Collectors.toSet());
  }
}
