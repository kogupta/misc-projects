package org.kogupta.diskStore.datagen;

import org.kogupta.diskStore.lmdbStore.RWTest;
import org.kogupta.diskStore.utils.BufferSize;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class DataGeneratorTest {
    @Test
    public void testGenerateUsing() throws IOException, InterruptedException {
        LocalDateTime start = LocalDate.parse("2018-01-01").atStartOfDay();
        LocalDateTime end = start.plusMinutes(10);
//        Path path = Files.createTempDirectory("fileSorter");
        Path path = Paths.get("/tmp/fileSorter");
        Files.createDirectories(path);

        System.out.println("Directory created: " + path.toAbsolutePath());

        Args args = new Args(start, end, path, 1, BufferSize.OneK);

        DataGenerator.generateUsing(args);

        RWTest.Args testArgs = RWTest.Args.fromDatagenArgs(args);
        RWTest.start(testArgs);

        TimeUnit.MINUTES.sleep(1);
    }
}