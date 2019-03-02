package org.kogu.misc;

class Person implements MutablePerson {
  private int age;
  private String name;

  @Override
  public int getAge() { return age;}

  public void setAge(int age) { this.age = age;}

  @Override
  public String getName() { return name;}

  public void setName(String name) { this.name = name;}
}
