package org.kogu.misc;

interface ImmutablePerson {
  int getAge();
  String getName();

  static ImmutablePerson createImmutablePerson(String name, int age) {
    return createMutablePerson(name, age);
  }

  static MutablePerson createMutablePerson(String name, int age) {
    Person person = new Person();
    person.setAge(age);
    person.setName(name);

    return person;
  }


}
