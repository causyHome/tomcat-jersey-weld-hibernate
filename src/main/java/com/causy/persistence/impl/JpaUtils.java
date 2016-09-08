package com.causy.persistence.impl;

import com.causy.persistence.PersistenceSingleton;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

class JpaUtils {

    static Object executeTransactionalJpaOperation(final JPATransactionalOperation operation, String errorMessage) {
        EntityManager em = PersistenceSingleton.instance.getEntityManagerFactory().createEntityManager();

        final EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            final Object returnedData = operation.execute(em);
            tx.commit();
            return returnedData;
        } catch (RollbackException e) {
            if (tx != null && tx.isActive()) {
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
