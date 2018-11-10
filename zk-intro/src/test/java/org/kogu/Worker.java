package org.kogu;

import java.io.Serializable;

public class Worker implements Serializable {
    public final String name;

    public Worker(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Worker other = (Worker) o;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Worker: " + name;
    }
}
