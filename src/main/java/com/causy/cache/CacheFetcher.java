package com.causy.cache;

import com.causy.model.CausyEntity;
import org.infinispan.Cache;

public class CacheFetcher<K, E extends CausyEntity> {

    Cache<K, E> fetchCache(final String cacheName) {
        return CacheSingleton.instance.getCacheManager().getCache(cacheName);
    }
}
