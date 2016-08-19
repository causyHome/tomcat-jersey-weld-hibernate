package com.causy.persistence.hibernate;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class PersistenceServletContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        SessionFactoryManager.instance.destroySessionFactory();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }
}
