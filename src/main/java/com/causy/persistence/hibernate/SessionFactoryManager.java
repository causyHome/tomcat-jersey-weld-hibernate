package com.causy.persistence.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.logging.Logger;

public enum SessionFactoryManager {
    instance;

    private SessionFactory sessionFactory;
    private final Logger logger = Logger.getLogger("SessionFactoryManagerLogger");


    private SessionFactory setupSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        logger.info("Hibernate Configuration created successfully");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        logger.info("ServiceRegistry created successfully");
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        logger.info("SessionFactory created successfully");
        return sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = setupSessionFactory();
        }
        return sessionFactory;
    }

    public void destroySessionFactory() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            logger.info("Closing sessionFactory");
            sessionFactory.close();
        }
        logger.info("Released Hibernate sessionFactory resource");
    }
}
