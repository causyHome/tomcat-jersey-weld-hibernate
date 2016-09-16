package com.causy.cache;

import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MINUTES;

public enum CacheSingleton {
    instance;


    private EmbeddedCacheManager cacheManager;

    public EmbeddedCacheManager getCacheManager() {
        if (cacheManager == null) {
            cacheManager = initCacheManager();
        }
        return cacheManager;
    }

    private EmbeddedCacheManager initCacheManager() {
        ConfigurationBuilder config = new ConfigurationBuilder();
        config.expiration().lifespan(30, MINUTES);
        return new DefaultCacheManager(config.build());
    }

    public void destroy() {
        cacheManager.stop();
    }

}
