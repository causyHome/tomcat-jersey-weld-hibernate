package com.causy.cache;

import com.causy.model.Employee;
import org.infinispan.Cache;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CacheHandlerTest {

    private static final int ENTITY_ID_AND_CACHE_KEY = 1;
    private static final String CACHE_NAME = "test";
    private static final Employee ENTITY = new Employee("test", "test");

    private final CacheFetcher<Integer, Employee> cacheFetcherMock = mock(CacheFetcher.class);
    private final CacheHandler<Integer, Employee> cacheHandler = new CacheHandler<>(cacheFetcherMock);

    private final Cache<Integer, Employee> testCache = CacheSingleton.instance.getCacheManager().getCache(CACHE_NAME);
    private final Cache<Integer, Employee> cacheSpy = spy(testCache);

    @Before
    public void beforeEach(){
        testCache.clear();
    }

    @Test
    public void should_get_entity_from_source_and_store_it_using_cacheKey_as_sourceParameter() {
        // given
        Function<Integer, Employee> sourceFunctionMock = mock(Function.class);

        when(cacheFetcherMock.fetchCache(CACHE_NAME)).thenReturn(cacheSpy);
        when(sourceFunctionMock.apply(any())).thenReturn(ENTITY);

        // when fetching a first time
        final Employee actual1 = cacheHandler.getEntityFromCache(CACHE_NAME).orFromSource(sourceFunctionMock).usingCacheKeyAsSourceParameter(ENTITY_ID_AND_CACHE_KEY);
        // when fetching a second time
        final Employee actual2 = cacheHandler.getEntityFromCache(CACHE_NAME).orFromSource(sourceFunctionMock).usingCacheKeyAsSourceParameter(ENTITY_ID_AND_CACHE_KEY);

        //then
        assertThat(actual1).isEqualTo(ENTITY);
        assertThat(actual2).isEqualTo(ENTITY);

        // cache storage should be executed only ONCE
        verify(cacheSpy, times(1)).put(ENTITY_ID_AND_CACHE_KEY, ENTITY);
        // entity retrieval from real source should be executed only ONCE
        verify(sourceFunctionMock, times(1)).apply(ENTITY_ID_AND_CACHE_KEY);
    }

    @Test
    public void should_get_entity_from_source_and_not_store_it_if_actual_retrieval_returned_nothing_using_cacheKey_as_sourceParameter() {
        // given
        Function<Integer, Employee> sourceFunctionMock = mock(Function.class);

        when(cacheFetcherMock.fetchCache(CACHE_NAME)).thenReturn(cacheSpy);
        when(sourceFunctionMock.apply(ENTITY_ID_AND_CACHE_KEY)).thenReturn(null);

        // when fetching a first time
        final Employee actual1 = cacheHandler.getEntityFromCache(CACHE_NAME).orFromSource(sourceFunctionMock).usingCacheKeyAsSourceParameter(ENTITY_ID_AND_CACHE_KEY);
        // when fetching a second time
        final Employee actual2 = cacheHandler.getEntityFromCache(CACHE_NAME).orFromSource(sourceFunctionMock).usingCacheKeyAsSourceParameter(ENTITY_ID_AND_CACHE_KEY);

        //then
        assertThat(actual1).isEqualTo(null);
        assertThat(actual2).isEqualTo(null);

        // cache storage should be executed 0 TIMES since no entity was retrieved from real source
        verify(cacheSpy, times(0)).put(ENTITY_ID_AND_CACHE_KEY, ENTITY);
        // entity retrieval from real source should be executed TWICE
        verify(sourceFunctionMock, times(2)).apply(anyInt());
    }

    @Test
    public void should_get_entity_from_source_and_store_it_using_cacheKey() {
        // given
        CacheHandler.Source<Employee> sourceMock = mock(CacheHandler.Source.class);

        when(cacheFetcherMock.fetchCache(CACHE_NAME)).thenReturn(cacheSpy);
        when(sourceMock.get()).thenReturn(ENTITY);

        // when fetching a first time
        final Employee actual1 = cacheHandler.getEntityFromCache(CACHE_NAME).orFromSource(sourceMock).usingCacheKey(ENTITY_ID_AND_CACHE_KEY);
        // when fetching a second time
        final Employee actual2 = cacheHandler.getEntityFromCache(CACHE_NAME).orFromSource(sourceMock).usingCacheKey(ENTITY_ID_AND_CACHE_KEY);

        //then
        assertThat(actual1).isEqualTo(ENTITY);
        assertThat(actual2).isEqualTo(ENTITY);

        // cache storage should be executed only ONCE
        verify(cacheSpy, times(1)).put(ENTITY_ID_AND_CACHE_KEY, ENTITY);
        // entity retrieval from real source should be executed only ONCE
        verify(sourceMock, times(1)).get();
    }


    @Test
    public void should_get_entity_from_source_and_not_store_it_if_actual_retrieval_returned_nothing_using_cacheKey() {
        // given
        CacheHandler.Source<Employee> sourceMock = mock(CacheHandler.Source.class);

        when(cacheFetcherMock.fetchCache(CACHE_NAME)).thenReturn(cacheSpy);
        when(sourceMock.get()).thenReturn(null);

        // when fetching a first time
        final Employee actual1 = cacheHandler.getEntityFromCache(CACHE_NAME).orFromSource(sourceMock).usingCacheKey(ENTITY_ID_AND_CACHE_KEY);
        // when fetching a second time
        final Employee actual2 = cacheHandler.getEntityFromCache(CACHE_NAME).orFromSource(sourceMock).usingCacheKey(ENTITY_ID_AND_CACHE_KEY);

        //then
        assertThat(actual1).isEqualTo(null);
        assertThat(actual2).isEqualTo(null);

        // cache storage should be executed 0 TIMES since no entity was retrieved from real source
        verify(cacheSpy, times(0)).put(ENTITY_ID_AND_CACHE_KEY, ENTITY);
        // entity retrieval from real source should be executed TWICE
        verify(sourceMock, times(2)).get();
    }
}