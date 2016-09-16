package com.causy.cache;

import org.infinispan.Cache;

import java.util.function.Function;

public class CacheHandler<K, E> {

    @FunctionalInterface
    public interface CacheFunction1<K, E> {
        CacheFunction2<K, E> orFromSource(Function<K, E> source);
    }

    @FunctionalInterface
    public interface CacheFunction2<K, E> {
        E usingEntityIdAsCacheKey(K cacheKey);
    }

    public CacheFunction1<K, E> getEntityFromCache(String cacheName) {
        Cache<K, E> cache = CacheSingleton.instance.getCacheManager().getCache(cacheName);
        return new CacheFunction1<K, E>() {
            @Override
            public CacheFunction2<K, E> orFromSource(Function<K, E> source) {
                return new CacheFunction2<K, E>() {
                    @Override
                    public E usingEntityIdAsCacheKey(K entityId) {
                        E entity = cache.get(entityId);
                        if (entity == null) {
                            entity = source.apply(entityId);
                            if (entity != null) {
                                cache.put(entityId, entity);
                            }
                        }
                        return entity;
                    }
                };
            }
        };
    }

}
