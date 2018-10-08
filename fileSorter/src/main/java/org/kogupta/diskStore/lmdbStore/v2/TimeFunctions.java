package org.kogupta.diskStore.lmdbStore.v2;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import static java.time.ZoneOffset.UTC;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

enum TimeFunctions {
    ;

    private static final long oneMilli = MILLISECONDS.toNanos(1);

    public static String asDate(LocalDate a) {
        String mon = a.getMonth().name().substring(0, 3);
        int n = a.getDayOfMonth();
        String _day = n < 10 ? "0" + n : Integer.toString(n);
        return a.getYear() + "-" + mon + "-" + _day;
    }

    public static LocalDate toLocalDate(long millis) {
        return Instant.ofEpochMilli(millis).atOffset(UTC).toLocalDate();
    }

    public static long toEpochMillis(LocalDate date) {
        return date.atStartOfDay().toInstant(UTC).toEpochMilli();
    }

    public static long endOfDay(LocalDate date) {
        LocalDateTime time = date.plusDays(1).atStartOfDay().minusNanos(oneMilli);
        return time.toInstant(UTC).toEpochMilli();
    }

    public static long endOfDay(long n) {
        OffsetDateTime time = Instant.ofEpochMilli(n).atOffset(UTC);
        long start = time.truncatedTo(ChronoUnit.DAYS).toInstant().toEpochMilli();
        return start + DAYS.toMillis(1) - 1;
    }

    public static long startOfNextDay(long n) {
        return endOfDay(n) + 1;
    }

}
