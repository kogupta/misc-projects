package org.kogu.workerCluster;

import com.tangosol.net.DefaultCacheServer;
import com.tangosol.net.DefaultConfigurableCacheFactory;

public class ClusterServer {
    public static void main(String[] args) {
        DefaultConfigurableCacheFactory factory = new DefaultConfigurableCacheFactory("example-config.xml");
        DefaultCacheServer dcs = new DefaultCacheServer(factory);
        dcs.startAndMonitor(5_000);


    }

}
