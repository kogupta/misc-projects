package org.kogu;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

class ResManImpl {
    public static void main(String[] args) {
        NamedCache cache = CacheFactory.getCache("mycache");
        System.out.println("Value in cache is: " + cache.get("key1"));
    }
}
