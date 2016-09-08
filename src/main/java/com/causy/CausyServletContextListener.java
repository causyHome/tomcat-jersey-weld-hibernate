package com.causy;


import com.causy.cache.CacheProducer;
import com.causy.persistence.hibernate.PersistenceSingleton;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

@WebListener
public class CausyServletContextListener implements javax.servlet.ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        CacheProducer.singleton.destroyCacheManager();
        PersistenceSingleton.instance.destroy();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }
}
