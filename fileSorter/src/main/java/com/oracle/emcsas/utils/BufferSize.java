package com.oracle.emcsas.utils;

import java.util.Optional;

public enum BufferSize {
    OneK {
        @Override public int intSize() { return 1024; }

        @Override public String synonym() { return "1k"; }
    },
    TwoK {
        @Override public int intSize() { return 2 * 1024; }

        @Override public String synonym() { return "2k"; }
    },
    FourK {
        @Override public int intSize() { return 4 * 1024; }

        @Override public String synonym() { return "4k"; }
    },
    HalfKb {
        @Override public int intSize() { return 512; }

        @Override public String synonym() { return "0.5k"; }
    },
    ;

    public abstract int intSize();
    public abstract String synonym();

    public static Optional<BufferSize> fromSize(int n) {
        for (BufferSize e : values()) {
            if (e.intSize() == n) {
                return Optional.of(e);
            }
        }

        return Optional.empty();
    }

    public static Optional<BufferSize> fromSynonym(String s) {
        for (BufferSize e : values()) {
            if (e.synonym().equals(s)) {
                return Optional.of(e);
            }
        }

        return Optional.empty();
    }

    public static String[] allPossibleValues() {
        BufferSize[] values = BufferSize.values();
        String[] xs = new String[values.length * 3]; // names, ints values, synonyms
        for (int i = 0; i < values.length; i++) {
            BufferSize value = values[i];
            xs[3 * i] = value.name();
            xs[3 * i + 1] = value.synonym();
            xs[3 * i + 2] = String.valueOf(value.intSize());
        }

        return xs;
    }
}
