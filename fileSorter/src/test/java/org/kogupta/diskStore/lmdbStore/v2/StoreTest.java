package org.kogupta.diskStore.lmdbStore.v2;

import com.google.common.io.MoreFiles;
import org.kogupta.diskStore.Pojo2;
import org.kogupta.diskStore.utils.Functions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.time.Month.JANUARY;
import static java.time.ZoneOffset.UTC;
import static java.util.concurrent.TimeUnit.DAYS;
import static org.testng.Assert.*;

public class StoreTest {
    private static final Path path = Paths.get("/tmp/fileSorter/lmdb-test");

    private Store store;

    @BeforeTest
    public void createStore() throws IOException {
        Path _path = Files.createDirectories(path);
        assertNotNull(_path);
        assertTrue(Files.exists(path));
        assertTrue(Files.isDirectory(path));

        store = new Store(path);
    }

    @AfterTest
    public void close() throws IOException {
        store.closeAll();
        MoreFiles.deleteRecursively(path);
        assertFalse(path.toFile().exists());
    }

    @Test
    public void bulkAddTest() throws IOException {
        int tenantCount = 3;
        String[] tenants = Functions.tenants(tenantCount);
        String[] secKeys = {"secKey1", "secKey2", "secKey3"};

        List<Pojo2> xs = new ArrayList<>();

        LocalDateTime time = LocalDate.of(2018, JANUARY, 1).atStartOfDay();
        Instant utcInstant = time.toInstant(UTC);
        Random r = new Random();
        int oneDay = (int) DAYS.toSeconds(1);
        for (String tenant : tenants) {
            for (String secKey : secKeys) {
                Pojo2 pojo2 = new Pojo2();
                pojo2.setUuid(UUID.randomUUID().toString());
                pojo2.setTenant(tenant);
                pojo2.setSecondaryKey(secKey);
                Instant n = utcInstant.plusSeconds(r.nextInt(oneDay));
                pojo2.setTimestamp(n.toEpochMilli());
                pojo2.setTime(n.toString());
                xs.add(pojo2);
            }
        }

        System.out.println(xs.stream()
                                   .map(Pojo2::toString)
                                   .collect(Collectors.joining("\n")));

        long numDays = xs.stream().mapToLong(Pojo2::getTimestamp)
                .mapToObj(Store::toLocalDate)
                .distinct()
                .count();
        assertEquals(numDays, 1);

        assertEquals(Files.list(path).count(), 0);
        store.bulkAdd(xs);

        // separate dirs for each tenant
        assertEquals(Files.list(path).count(), tenantCount);

        for (String tenant : tenants) {
            // separate dir for each day
            assertEquals(Files.list(path.resolve(tenant)).count(), numDays);
        }
    }

    @Test
    public void bulkAddTest2() throws IOException {
        int tenantCount = 1;
        String[] tenants = Functions.tenants(tenantCount);
        String[] secKeys = {"secKey1", "secKey2", "secKey3"};

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

        xs.stream()
                .map(Pojo2::toString)
                .forEach(System.out::println);

        long numDays = xs.stream().mapToLong(Pojo2::getTimestamp)
                .mapToObj(Store::toLocalDate)
                .distinct()
                .count();
        assertEquals(numDays, secKeys.length);

        assertEquals(Files.list(path).count(), 0);
        store.bulkAdd(xs);

        // separate dirs for each tenant
        assertEquals(Files.list(path).count(), tenantCount);

        for (String tenant : tenants) {
            // separate dir for each day
            assertEquals(Files.list(path.resolve(tenant)).count(), numDays);
        }
    }


}