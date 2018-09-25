package org.kogupta.diskStore.datagen;

import org.kogupta.diskStore.utils.BufferSize;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.kogupta.diskStore.utils.Functions.getString;

public final class DataChecker {
    private static final Path input = Paths.get("/tmp/fileSorter/data.bin");
    private static final int len = (int) TimeUnit.MINUTES.toMillis(5);
    private static final int payload = BufferSize.OneK.intSize();
    private static final Set<String> tenants = new LinkedHashSet<>();

    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(len * payload);
        try (SeekableByteChannel channel = Files.newByteChannel(input)) {
            while (channel.read(buffer) != -1) {
                buffer.flip();
                final int end = buffer.limit();
                for (int start = 0; start != end; start = buffer.limit()) {
                    int delta = Math.min(end - start, payload);
                    buffer.position(start).limit(start + delta);
                    ByteBuffer bb = buffer.slice();
                    bb.position(Integer.BYTES + Long.BYTES);
                    String s = getString(bb);
                    tenants.add(s);
                }

                buffer.clear();
            }
        }

        System.out.println(tenants);
    }
}
