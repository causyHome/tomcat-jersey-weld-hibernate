package com.causy.persistence.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

class HibernateUtils {

    static Object performHibernateOperation(final HibernateOperationStrategy operation, String errorMessage) {
        SessionFactory sessionFactory = SessionFactoryManager.instance.getSessionFactory();

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
            session.close();
        }
    }
}
