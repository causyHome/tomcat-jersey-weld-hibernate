package com.causy.cache;

import org.infinispan.Cache;

import java.util.function.Function;

public class CacheHandler {

    @FunctionalInterface
    public interface CacheFunction1<K, E> {
        CacheFunction2<K, E> orFromSource(Function<K, E> source);
    }

    @FunctionalInterface
    public interface CacheFunction2<K, E> {
        E usingCacheKey(K cacheKey);
    }


    public static CacheFunction1<Integer, Object> getEntityFromCache(String cacheName) {
        Cache cache = CacheProducer.singleton.getCacheManager().getCache(cacheName);
        return new CacheFunction1<Integer, Object>() {
            @Override
            public CacheFunction2<Integer, Object> orFromSource(Function<Integer, Object> source) {
                return new CacheFunction2<Integer, Object>() {
                    @Override
                    public Object usingCacheKey(Integer entityId) {
                        Object entity = cache.get(entityId);
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
