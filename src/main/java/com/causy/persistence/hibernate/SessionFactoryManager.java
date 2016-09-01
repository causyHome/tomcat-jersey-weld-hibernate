package com.causy.persistence.hibernate;

import com.causy.model.Employee;
import com.causy.model.Team;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public enum SessionFactoryManager  {
    singleton;

    private SessionFactory sessionFactory;
    private EntityManagerFactory entityManagerFactory;
    private final Logger logger = Logger.getLogger("SessionFactoryManagerLogger");


    private EntityManagerFactory setupEntityManagerFactory() {
        return Persistence.createEntityManagerFactory( "com.causy.poc.persistence" );
    }

    private SessionFactory setupSessionFactory() {
        Configuration configuration = new Configuration();

        Properties defaultProps = new Properties();
        try (final InputStream stream =
                     Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.properties")) {
            defaultProps.load(stream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        configuration.addProperties(defaultProps);

        configuration.addAnnotatedClass(Employee.class);
        configuration.addAnnotatedClass(Team.class);

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

    public EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null){
            entityManagerFactory = setupEntityManagerFactory();
        }
        return entityManagerFactory;
    }
}
