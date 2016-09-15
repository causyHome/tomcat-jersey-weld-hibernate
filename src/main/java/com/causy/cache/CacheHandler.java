package com.causy.cache;

import org.infinispan.Cache;

import java.util.function.Function;

public class CacheHandler {

    public static Function<Integer, Object> getEntityFromCacheOrFrom(String cacheName, Function<Integer, Object> nonCachedEntitySource) {
        Cache cache = CacheProducer.singleton.getCacheManager().getCache(cacheName);

        return new Function<Integer, Object>() {
            @Override
            public Object apply(Integer id) {
                Object entity = cache.get(id);
                if (entity == null) {
                    entity = nonCachedEntitySource.apply(id);
                    if (entity != null) {
                        cache.put(id, entity);
                    }
                }
                return entity;
            }
        };
    }

}
