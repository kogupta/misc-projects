package org.kogupta.diskStore.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileUtils {
    private FileUtils() {}

    public static Path mkdir(Path p) throws IOException {
        boolean exists = Files.exists(p);
        boolean isDir = Files.isDirectory(p);
        if (exists && isDir) return p;

        if (exists)
            throw new IllegalStateException("Path [" + p + "] exists but is NOT DIRECTORY!");

        return Files.createDirectories(p);
    }
}
