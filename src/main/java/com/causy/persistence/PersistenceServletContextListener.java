package com.causy.persistence;


import com.causy.persistence.hibernate.SessionFactoryManager;
import org.hibernate.SessionFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Logger;

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
