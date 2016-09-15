package com.causy.cache;

import org.infinispan.Cache;

import java.util.function.Function;

public class CacheHandler<E> {

    @FunctionalInterface
    public interface CacheFunction1<K, E> {
        CacheFunction2<K, E> orFromSource(Function<K, E> source);
    }

    @FunctionalInterface
    public interface CacheFunction2<K, E> {
        E usingCacheKey(K cacheKey);
    }

    public CacheFunction1<Integer, E> getEntityFromCache(String cacheName) {
        Cache<Integer, E> cache = CacheProducer.singleton.getCacheManager().getCache(cacheName);
        return new CacheFunction1<Integer, E>() {
            @Override
            public CacheFunction2<Integer, E> orFromSource(Function<Integer, E> source) {
                return new CacheFunction2<Integer, E>() {
                    @Override
                    public E usingCacheKey(Integer entityId) {
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
