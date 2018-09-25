package org.kogupta.diskStore.datagen;


import org.testng.annotations.Test;

public class ArgsTest {
    @Test
    void requiredParams() {
        catchException(() -> Args.parse("--from-date"));
        catchException(() -> Args.parse("--to-date"));
        catchException(() -> Args.parse("--data-dir"));
    }

    @Test
    void parse() {
        catchException(() -> Args.parse("--from-date 2018-01-01"));
        catchException(() -> Args.parse("--to-date 2018-01-01"));
        catchException(() -> Args.parse("--data-dir /tmp/fileSorter"));
    }

    private void catchException(UncheckedRunnable r) {
        try {
            r.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    interface UncheckedRunnable {
        void run() throws Exception;
    }
}