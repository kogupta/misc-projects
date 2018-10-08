package org.kogupta.diskStore.lmdbStore.v2;

import com.google.common.io.MoreFiles;
import org.kogupta.diskStore.Pojo2;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.time.Month.JANUARY;
import static java.time.ZoneOffset.UTC;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static org.kogupta.diskStore.utils.Functions.tenants;
import static org.testng.Assert.*;

public class StoreTest {
    private static final Path path = Paths.get("/tmp/fileSorter/lmdb-test");

    private Store store;

    @BeforeMethod
    public void createStore() throws IOException {
        Path _path = Files.createDirectories(path);
        assertNotNull(_path);
        assertTrue(Files.exists(path));
        assertTrue(Files.isDirectory(path));

        store = new Store(path);
    }

    @AfterMethod
    public void close() throws IOException {
        store.closeAll();
        MoreFiles.deleteRecursively(path);
        assertFalse(path.toFile().exists());
    }

    @Test
    public void addAndCount() throws IOException {
        String[] tenants = tenants(3);
        String[] secKeys = secondaryKeys(3);

        List<Pojo2> xs = new ArrayList<>();

        LocalDateTime time = LocalDate.of(2018, JANUARY, 1).atStartOfDay();
        Instant utcInstant = time.toInstant(UTC);
        int oneHour = (int) HOURS.toSeconds(1);
        for (String tenant : tenants) {
            for (String secKey : secKeys) {
                Pojo2 pojo2 = new Pojo2();
                pojo2.setUuid(UUID.randomUUID().toString());
                pojo2.setTenant(tenant);
                pojo2.setSecondaryKey(secKey);
                Instant n = utcInstant.plusSeconds(oneHour);
                pojo2.setTimestamp(n.toEpochMilli());
                pojo2.setTime(n.toString());
                xs.add(pojo2);
            }
        }

        xs.stream()
                .map(Pojo2::toString)
                .forEach(System.out::println);

        long numDays = xs.stream().mapToLong(Pojo2::getTimestamp)
                .mapToObj(TimeFunctions::toLocalDate)
                .distinct()
                .count();
        assertEquals(numDays, 1);

        assertEquals(Files.list(path).count(), 0);
        store.bulkAdd(xs);

        // separate dirs for each tenant
        assertEquals(Files.list(path).count(), tenants.length);

        for (String tenant : tenants) {
            // separate dir for each day
            assertEquals(Files.list(path.resolve(tenant)).count(), numDays);
        }

        long from = utcInstant.toEpochMilli();
        long to = from + DAYS.toMillis(1);
        for (String tenant : tenants) {
            for (String secKey : secKeys) {
                int n = store.countKeys(from, to, tenant, secKey);
                assertEquals(n, 1);

                List<Pojo2> read = store.read(from, to, tenant, secKey);
                assertNotNull(read);
                assertEquals(read.size(), 1);
                assertEquals(read.get(0).getTenant(), tenant);
                assertEquals(read.get(0).getSecondaryKey(), secKey);
            }
        }

    }

    @Test
    public void addAndCount2() throws IOException {
        String[] tenants = tenants(1);
        String[] secKeys = secondaryKeys(3);

        List<Pojo2> xs = new ArrayList<>();

        LocalDateTime time = LocalDate.of(2018, JANUARY, 1).atStartOfDay();
        Instant utcInstant = time.toInstant(UTC);
        for (String tenant : tenants) {
            for (int i = 0; i < secKeys.length; i++) {
                String secKey = secKeys[i];
                Pojo2 pojo2 = new Pojo2();
                pojo2.setUuid(UUID.randomUUID().toString());
                pojo2.setTenant(tenant);
                pojo2.setSecondaryKey(secKey);
                Instant n = utcInstant.plus(i, ChronoUnit.DAYS);
                pojo2.setTimestamp(n.toEpochMilli());
                pojo2.setTime(n.toString());
                xs.add(pojo2);
            }
        }

        // all items added
        xs.stream()
                .map(Pojo2::toString)
                .forEach(System.out::println);

        long numDays = xs.stream().mapToLong(Pojo2::getTimestamp)
                .mapToObj(TimeFunctions::toLocalDate)
                .distinct()
                .count();
        assertEquals(numDays, secKeys.length);

        // assert the dir is empty before test
        assertEquals(Files.list(path).count(), 0);
        store.bulkAdd(xs);

        // separate dirs for each tenant
        assertEquals(Files.list(path).count(), tenants.length);

        for (String tenant : tenants) {
            // separate dir for each day
            assertEquals(Files.list(path.resolve(tenant)).count(), numDays);
        }

        long from = utcInstant.toEpochMilli();
        for (String tenant : tenants) {
            for (int i = 0; i < secKeys.length; i++) {
                String secKey = secKeys[i];
                long to = from + DAYS.toMillis(i + 1);
                int n = store.countKeys(from, to, tenant, secKey);
                assertEquals(n, 1);

                List<Pojo2> read = store.read(from, to, tenant, secKey);
                assertNotNull(read);
                assertEquals(read.size(), 1);
                assertEquals(read.get(0).getTenant(), tenant);
                assertEquals(read.get(0).getSecondaryKey(), secKey);
            }
        }

    }

    @Test
    public void addAndCount3() throws IOException {
        String[] tenants = tenants(1);
        String[] secKeys = secondaryKeys(1);

        List<Pojo2> xs = new ArrayList<>();

        LocalDateTime time = LocalDate.of(2018, JANUARY, 1).atStartOfDay();
        Instant utcInstant = time.toInstant(UTC);

        final int days = 2, hours = 48;
        final String tenant = tenants[0], secKey = secKeys[0];

        for (int i = 0; i < hours; i++) {
            Pojo2 pojo2 = new Pojo2();
            pojo2.setUuid(UUID.randomUUID().toString());
            pojo2.setTenant(tenant);
            pojo2.setSecondaryKey(secKey);
            Instant n = utcInstant.plus(i, ChronoUnit.HOURS);
            pojo2.setTimestamp(n.toEpochMilli());
            pojo2.setTime(n.toString());
            xs.add(pojo2);
        }

        Collections.shuffle(xs);

        // all items added
        xs.stream()
                .map(Pojo2::toString)
                .forEach(System.out::println);

        long numDays = xs.stream().mapToLong(Pojo2::getTimestamp)
                .mapToObj(TimeFunctions::toLocalDate)
                .distinct()
                .count();
        assertEquals(numDays, days);

        // assert the dir is empty before test
        assertEquals(Files.list(path).count(), 0);
        store.bulkAdd(xs);

        // separate dirs for each tenant
        assertEquals(Files.list(path).count(), tenants.length);

        for (String _tenant : tenants) {
            // separate dir for each day
            assertEquals(Files.list(path.resolve(_tenant)).count(), numDays);
        }

        long from = utcInstant.toEpochMilli();
        for (int i = 1; i <= hours; i++) {
            long to = from + HOURS.toMillis(i);
            int n = store.countKeys(from, to, tenant, secKey);
            assertEquals(n, i);

            List<Pojo2> read = store.read(from, to, tenant, secKey);
            assertNotNull(read);
            assertEquals(read.size(), i);
            assertTrue(read.stream().map(Pojo2::getTenant).allMatch(tenant::equals));
            assertTrue(read.stream().map(Pojo2::getSecondaryKey).allMatch(secKey::equals));
            isSorted(read);
        }
    }

    private static void isSorted(List<Pojo2> xs) {
        for (int i = 0; i < xs.size() - 1; i++) {
            Pojo2 a = xs.get(i);
            Pojo2 b = xs.get(i + 1);
            assertTrue(a.getTimestamp() <= b.getTimestamp());
        }

    }

    private static String[] secondaryKeys(int n) {
        assert n > 0 && n < 100;
        String[] xs = new String[n];
        Arrays.setAll(xs, i -> i < 10 ? "secKey_0" + i : "secKey_" + i);
        return xs;
    }
}