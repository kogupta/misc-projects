package org.kogu.workerCluster;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.net.cache.TypeAssertion;
import com.tangosol.util.Base;

import java.util.concurrent.TimeUnit;

import static org.kogu.workerCluster.Util.workerCache;

public class Worker2 extends Base {
    public static void main(String[] args) throws InterruptedException {
        CacheFactory.ensureCluster();

        TypeAssertion.WithTypesAssertion<String, String> option = new TypeAssertion.WithTypesAssertion<>(String.class, String.class);
        NamedCache<String, String> workers = CacheFactory.getTypedCache(workerCache, option);

        out("# of entries in `workers` cache: " + workers.size());

        TimeUnit.MINUTES.sleep(1);
    }
}
