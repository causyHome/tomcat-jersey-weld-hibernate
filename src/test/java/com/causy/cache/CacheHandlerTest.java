package com.causy.cache;

import org.infinispan.Cache;
import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CacheHandlerTest {

    private static final int ENTITY_ID_AND_CACHE_KEY = 1;
    private static final String CACHE_NAME = "test";
    private static final String ENTITY = "result";

    private final CacheFetcher<Integer, String> cacheFetcherMock = mock(CacheFetcher.class);
    private final CacheHandler<Integer, String> cacheHandler = new CacheHandler<>(cacheFetcherMock);
    private final CacheHandler.Source<String> sourceMock = mock(CacheHandler.Source.class);
    private final Function<Integer, String> sourceFunctionMock = mock(Function.class);


    @Test
    public void should_get_entity_from_source_and_store_it_using_entityID_as_cacheKey() {
        // given


        final Cache<Integer, String> testCache = CacheSingleton.instance.getCacheManager().getCache(CACHE_NAME);
        testCache.clear();
        Cache<Integer, String> cacheSpy = spy(testCache);


        when(cacheFetcherMock.fetchCache(CACHE_NAME)).thenReturn(cacheSpy);
        when(sourceFunctionMock.apply(any())).thenReturn(ENTITY);

        // when
        final String actual = cacheHandler.getEntityFromCache(CACHE_NAME).orFromSource(sourceFunctionMock).usingCacheKeyAsSourceParameter(ENTITY_ID_AND_CACHE_KEY);
        assertThat(actual).isEqualTo(ENTITY);


        // when fetching a second
        cacheHandler.getEntityFromCache(CACHE_NAME).orFromSource(sourceFunctionMock).usingCacheKeyAsSourceParameter(ENTITY_ID_AND_CACHE_KEY);
        //then
        verify(cacheSpy, times(1)).put(ENTITY_ID_AND_CACHE_KEY, ENTITY);
        verify(sourceFunctionMock, times(1)).apply(ENTITY_ID_AND_CACHE_KEY);
    }


    @Test
    public void should_get_entity_from_source_and_store_it_using_cacheKey() {
        // given
        final Cache<Integer, String> testCache = CacheSingleton.instance.getCacheManager().getCache(CACHE_NAME);
        testCache.clear();
        Cache<Integer, String> cacheSpy = spy(testCache);


        when(cacheFetcherMock.fetchCache(CACHE_NAME)).thenReturn(cacheSpy);
        when(sourceMock.get()).thenReturn(ENTITY);

        // when
        final String actual = cacheHandler.getEntityFromCache(CACHE_NAME).orFromSource(sourceMock).usingCacheKey(ENTITY_ID_AND_CACHE_KEY);
        assertThat(actual).isEqualTo(ENTITY);


        // when fetching a second time
        cacheHandler.getEntityFromCache(CACHE_NAME).orFromSource(sourceMock).usingCacheKey(ENTITY_ID_AND_CACHE_KEY);

        //then
        verify(cacheSpy, times(1)).put(ENTITY_ID_AND_CACHE_KEY, ENTITY);
        verify(sourceMock, times(1)).get();
    }
}