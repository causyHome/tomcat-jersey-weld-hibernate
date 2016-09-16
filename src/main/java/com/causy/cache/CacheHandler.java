package com.causy.cache;

import com.causy.model.CausyEntity;
import org.infinispan.Cache;

import javax.inject.Inject;
import java.util.function.Function;

public class CacheHandler<K, E extends CausyEntity> {

    private final CacheFetcher<K, E> cacheFetcher;

    @Inject
    public CacheHandler(CacheFetcher<K, E> cacheFetcher) {
        this.cacheFetcher = cacheFetcher;
    }

    public interface CacheIntermediate<K, E> {
        CacheFunction2<K, E> orFromSource(Function<K, E> source);
        CacheFunction1<K, E> orFromSource(Source<E> source);
    }

    public interface Source<E>{
        E get();
    }

    @FunctionalInterface
    public interface CacheFunction1<K, E> {
        E usingCacheKey(K cacheKey);
    }

    @FunctionalInterface
    public interface CacheFunction2<K, E> {
        E usingCacheKeyAsSourceParameter(K cacheKey);
    }

    public CacheIntermediate<K, E> getEntityFromCache(String cacheName) {
        Cache<K, E> cache = cacheFetcher.fetchCache(cacheName);
        return new CacheIntermediate<K, E>() {
            @Override
            public CacheFunction2<K, E> orFromSource(Function<K, E> source) {
                return new CacheFunction2<K, E>() {
                    @Override
                    public E usingCacheKeyAsSourceParameter(K entityId) {
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

            @Override
            public CacheFunction1<K, E> orFromSource(Source<E> source) {
                return new CacheFunction1<K, E>() {
                    @Override
                    public E usingCacheKey(K entityId) {
                        E entity = cache.get(entityId);
                        if (entity == null) {
                            entity = source.get();
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
