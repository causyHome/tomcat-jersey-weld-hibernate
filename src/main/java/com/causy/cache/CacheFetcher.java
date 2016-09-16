package com.causy.cache;

import org.infinispan.Cache;

public class CacheFetcher<K, E> {

    Cache<K, E> fetchCache(final String cacheName) {
        return CacheSingleton.instance.getCacheManager().getCache(cacheName);
    }
}
