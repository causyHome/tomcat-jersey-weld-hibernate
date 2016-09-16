package com.causy.cache;

import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

import static java.util.concurrent.TimeUnit.MINUTES;

public enum CacheSingleton {
    instance;

    private EmbeddedCacheManager cacheManager;

    EmbeddedCacheManager getCacheManager() {
        if (cacheManager == null) {
            ConfigurationBuilder config = new ConfigurationBuilder();
            config.expiration().lifespan(30, MINUTES);
            cacheManager = new DefaultCacheManager(config.build());
        }
        return cacheManager;
    }

    public void destroy() {
        cacheManager.stop();
    }

}
