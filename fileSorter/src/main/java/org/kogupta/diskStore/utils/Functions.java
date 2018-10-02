package org.kogupta.diskStore.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.kogupta.diskStore.Pojo;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;

import static java.nio.charset.StandardCharsets.UTF_8;

public enum Functions {
    ;

    public static void require(boolean predicate, String message) {
        if (!predicate) {
            throw new IllegalArgumentException(message);
        }
    }

    public static long toMillis(LocalDateTime time) {
        return time.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public static void putString(ByteBuffer buffer, String s) {
        byte[] _tenant = s.getBytes(UTF_8);
        buffer.putInt(_tenant.length);
        buffer.put(_tenant);
    }

    public static String getString(ByteBuffer buffer) {
        int len = buffer.getInt();
        byte[] bytes = new byte[len];
        buffer.get(bytes);
        return new String(bytes, UTF_8);
    }

    public static void writeToBB(Pojo pojo, ByteBuffer buffer) {
        buffer.putInt(pojo.getId());
        buffer.putLong(pojo.getTimestamp());
        putString(buffer, pojo.getTenantId());
        putString(buffer, pojo.getPayload());
    }

    public static Pojo fromByteBuffer(ByteBuffer buffer) {
        Pojo pojo = new Pojo();
        pojo.setId(buffer.getInt());
        pojo.setTimestamp(buffer.getLong());
        pojo.setTenantId(getString(buffer));
        pojo.setPayload(getString(buffer));

        return pojo;
    }

    public static String[] tenants(int n) {
        String[] xs = new String[n];
        Arrays.setAll(xs, i -> i < 10 ? "tenant_0" + i : "tenant_" + i);
        return xs;
    }

    public static Optional<Integer> tryParse(String s) {
        try {
            int anInt = Integer.parseInt(s);
            return Optional.of(anInt);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static String bb(ByteBuffer bb) {
        return String.format("direct: %s, position: %,d, limit: %,d, capacity: %,d",
                             bb.isDirect(), bb.position(), bb.limit(), bb.capacity());
    }

    public static ThreadFactory namedTF(String name) {
        return new ThreadFactoryBuilder()
                .setNameFormat(name)
                .build();
    }

    public static LocalDateTime fromMillis(long millis) {
        OffsetDateTime instant = Instant.ofEpochMilli(millis).atOffset(ZoneOffset.UTC);
        return instant.toLocalDateTime();
    }

    public static void bbIterator(ByteBuffer buffer, int stride, Consumer<ByteBuffer> consumer) {
        final int end = buffer.limit();

        for (int start = 0; start != end; start = buffer.limit()) {
            int delta = Math.min(end - start, stride);
            buffer.position(start).limit(start + delta);
            consumer.accept(buffer.slice());
        }
    }
}
