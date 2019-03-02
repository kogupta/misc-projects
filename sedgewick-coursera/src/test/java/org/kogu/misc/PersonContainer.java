package org.kogu.misc;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.RandomAccess;

class PersonContainer implements RandomAccess {
  private final ArrayList<String> names;
  private final int[] ages;

  PersonContainer(int size) {
    ages = new int[size];
    names = new ArrayList<>(size);
  }

  public void store(Person p) {
    names.add(p.getName());
    ages[names.size() - 1] = p.getAge();
  }

  public MutablePerson atIndex(int i) {
    Preconditions.checkArgument(0 <= i && i <= names.size() - 1);
    return ImmutablePerson.createMutablePerson(names.get(i), ages[i]);
  }

  public static void main(String[] args) {
    // create
  }
}
