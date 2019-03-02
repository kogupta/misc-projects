package org.kogu.misc;

interface MutablePerson extends ImmutablePerson {
  void setAge(int age);
  void setName(String name);

  default ImmutablePerson asImmutable() {
    return this;
  }
}
