package org.kogupta.diskStore.utils;

import java.time.Instant;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

public final class Bucket {
    private static final String[] hours = _hours();
    private static final String[] months = _months();

    public final String date;
    public final int hour;

    private Bucket(long millis) {
        OffsetDateTime time = Instant.ofEpochMilli(millis).atOffset(ZoneOffset.UTC);
        int year = time.getYear();
        String month = months[time.getMonth().ordinal()];
        this.date = year + "-" + month + "-" + time.getDayOfMonth();
        this.hour = time.getHour();
    }

    @Override
    public String toString() {
        return "Bucket: [" + date + ", " + hours[hour] + "]";
    }

    public static Bucket create(long millis) {
        assert millis > 0;
        return new Bucket(millis);
    }

    private static String[] _hours() {
        String[] xs = new String[24];
        Arrays.setAll(xs, i -> i < 10 ? "0" + i : Integer.toString(i));
        return xs;
    }

    private static String[] _months() {
        Month[] months = Month.values();
        String[] xs = new String[months.length];
        Arrays.setAll(xs, i -> months[i].name().substring(0, 3));
        return xs;
    }

    public static void main(String[] args) {
        OffsetDateTime time = Instant.now().atOffset(ZoneOffset.UTC);
        long milli = time.toInstant().toEpochMilli();
        Bucket bucket = create(milli);
        System.out.println(bucket);
    }
}
