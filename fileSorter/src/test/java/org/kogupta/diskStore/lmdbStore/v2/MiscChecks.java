package org.kogupta.diskStore.lmdbStore.v2;

import org.testng.annotations.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;

import static java.util.concurrent.TimeUnit.HOURS;
import static org.kogupta.diskStore.lmdbStore.v2.TimeFunctions.toLocalDate;
import static org.testng.Assert.*;

@Test
public class MiscChecks {
    @Test
    public void range() {
        int n = 0;
        for (int i = 10; i <= 10; i++) {
            n++;
        }

        assertEquals(n, 1);
    }

    @Test
    public void timeRange() {
        LocalDate date = LocalDate.of(2018, Month.APRIL, 10);
        Instant instant = date.atStartOfDay().toInstant(ZoneOffset.UTC);
        long zero = instant.toEpochMilli();
        long a = zero + HOURS.toMillis(1);
        long b = zero + HOURS.toMillis(2);

        // assert same day
        assertTrue(toLocalDate(a).equals(toLocalDate(b)));

        long c = zero - HOURS.toMillis(2);
        assertFalse(toLocalDate(a).equals(toLocalDate(c)));
        blah(c, b, date.minusDays(1), date);
    }

    private void blah(long a, long b, LocalDate dateA, LocalDate dateB) {
        assertTrue(a < b);
        LocalDate start = toLocalDate(a);
        assertEquals(start, dateA);
        LocalDate end = toLocalDate(b);
        assertEquals(end, dateB);

        int n = 0;
        for (LocalDate i = start; !i.equals(end); i = i.plusDays(1)) {
            n++;
            long endOfDay = TimeFunctions.endOfDay(i);
            assertTrue(sameDay(TimeFunctions.toEpochMillis(i), endOfDay));
            assertFalse(sameDay(TimeFunctions.toEpochMillis(i), endOfDay + 1));
        }

        assertEquals(n, 1);
    }

    private static boolean sameDay(long a, long b) {
        return toLocalDate(a).equals(toLocalDate(b));
    }
}
