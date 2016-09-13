package com.causy.cache;

import org.infinispan.Cache;

public class CacheHandler {

    public static Object getEntityFromCacheOr(NonCachedEntitySource source, int id, Class cachedEntityClass) {
        Cache cache = CacheProducer.singleton.getCacheManager().getCache(cachedEntityClass.getCanonicalName() + "-CACHE");

        Object entity = cache.get(id);
        if (entity == null) {
            entity = source.get();
            if (entity != null) {
                cache.put(id, entity);
            }
        }
        return entity;
    }
}
