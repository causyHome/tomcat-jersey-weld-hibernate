package com.causy;


import com.causy.cache.CacheProducer;
import com.causy.persistence.hibernate.SessionFactoryManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CausyServletContextListener implements javax.servlet.ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        SessionFactoryManager.singleton.destroySessionFactory();
        CacheProducer.singleton.destroyCacheManager();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }
}
