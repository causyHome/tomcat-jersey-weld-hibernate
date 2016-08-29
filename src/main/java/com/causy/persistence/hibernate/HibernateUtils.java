package com.causy.persistence.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

class HibernateUtils {

    static Object executeTransactionalHibernateOperation(final HibernateTransactionalOperation operation, String errorMessage) {
        SessionFactory sessionFactory = SessionFactoryManager.singleton.getSessionFactory();

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            final Object returnedData = operation.execute(session);
            tx.commit();
            return returnedData;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new IllegalStateException(errorMessage, e);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    static Object executeTransactionalJPAOperation(final JPATransactionalOperation operation, String errorMessage) {
        EntityManager em = SessionFactoryManager.singleton.getEntityManagerFactory().createEntityManager();

        final EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            final Object returnedData = operation.execute(em);
            tx.commit();
            return returnedData;
        } catch (RollbackException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new IllegalStateException(errorMessage, e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}
