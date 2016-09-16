package com.causy;


import com.causy.cache.CacheSingleton;
import com.causy.persistence.PersistenceSingleton;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

@WebListener
public class CausyServletContextListener implements javax.servlet.ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        CacheSingleton.instance.destroy();
        PersistenceSingleton.instance.destroy();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }
}
