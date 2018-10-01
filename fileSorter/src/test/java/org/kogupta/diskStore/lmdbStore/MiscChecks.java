package org.kogupta.diskStore.lmdbStore;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

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
}
