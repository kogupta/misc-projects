package interview.epi.sorting;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

import static interview.epi.array.SortFunctions.assertionStatus;
import static java.util.stream.Collectors.joining;

// given: an array with duplicates
// todo: bring items with same `key` together
// note: need not do sorting
// constraint: number of elements >> universe of key
// partition used here is similar in sense as in usual data applications
public final class PartitionArray {
  public static void main(String[] args) {
    assertionStatus();

    List<Person> people = Arrays.asList(
        Person.of("Greg", 14),
        Person.of("John", 12),
        Person.of("Andy", 11),
        Person.of("Jim", 13),
        Person.of("Phil", 12),
        Person.of("Bob", 13),
        Person.of("Chip", 13),
        Person.of("Tim", 14)
    );

    Function<Person, Integer> fn = person -> person.age;
    Map<Integer, Integer> keyCount = keyCount(people, fn);
    Map<Integer, Integer> keyOffsets = keyOffset(keyCount);

    partition(keyOffsets, people, fn, keyCount);

    System.out.println(people.stream()
        .map(p -> p.name + ", " + p.age)
        .collect(joining(", ")));
  }

  private static <T, K> void partition(Map<K, Integer> keyOffsets,
                                       List<T> ts,
                                       Function<T, K> keyExtractor,
                                       Map<K, Integer> keyCounts) {
    while (!keyOffsets.isEmpty()) {
      Map.Entry<K, Integer> key2Offset = keyOffsets.entrySet().iterator().next();
      int index = key2Offset.getValue();

      K key = keyExtractor.apply(ts.get(index));
      int offset = keyOffsets.get(key);

      System.out.println(index + " <-> " + offset);
      Collections.swap(ts, index, offset);

      int nextCount = keyCounts.get(key) - 1;
      keyCounts.put(key, nextCount);
      if (nextCount > 0) {
        keyOffsets.put(key, offset + 1);
      } else {
        keyOffsets.remove(key);
      }
    }
  }

  @NotNull
  private static <T, K> Map<K, Integer> keyCount(Iterable<T> people,
                                                 Function<T, K> keyExtractor) {
    Map<K, Integer> xs = new HashMap<>();
    for (T t : people) {
      xs.merge(keyExtractor.apply(t), 1, Integer::sum);
    }

    System.out.println("counts: " + xs);

    return xs;
  }

  private static <K> Map<K, Integer> keyOffset(Map<K, Integer> keyCount) {
    Map<K, Integer> offsets = new HashMap<>();
    int offset = 0;
    for (Map.Entry<K, Integer> pair : keyCount.entrySet()) {
      offsets.put(pair.getKey(), offset);
      offset += pair.getValue();
    }

    System.out.println("offsets: " + offsets);

    return offsets;
  }

  private static final class Person {
    final String name;
    final int age;

    private Person(String name, int age) {
      this.name = name;
      this.age = age;
    }

    static Person of(String name, int age) {
      return new Person(name, age);
    }
  }
}
