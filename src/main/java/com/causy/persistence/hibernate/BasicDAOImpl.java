package com.causy.persistence.hibernate;

import com.causy.persistence.dao.BasicDAO;

import java.util.List;

import static com.causy.persistence.hibernate.HibernateUtils.executeTransactionalJPAOperation;

public class BasicDAOImpl implements BasicDAO {
    @Override
    public Object create(Object newEntity) {
        return executeTransactionalJPAOperation(session -> {
            session.persist(newEntity);
            return newEntity;
        }, "Could not create entity!");
    }

    @Override
    public Object get(Class entityClass, int entityId) {
        return executeTransactionalJPAOperation(em -> em.find(entityClass, entityId),
                String.format("Could not find entity with Id %s", entityId));
    }

    @Override
    public Object update(Object entity) {
        return executeTransactionalJPAOperation(em -> {
            em.merge(entity);
            return entity;
        }, String.format("Something went wrong when trying to update entity %s", entity));
    }

    @Override
    public List list(Class entityClass) {
        return (List) executeTransactionalJPAOperation(em ->
                        em.createQuery("from " + entityClass.getSimpleName()).getResultList(),
                String.format("Could not get list of entity %s", entityClass.getSimpleName()));
    }

    @Override
    public long count(Class entityClass) {
        return (long) executeTransactionalJPAOperation(
                em -> em.createQuery("select count(*) from " + entityClass.getSimpleName()).getSingleResult(),
                "Could not get count of entity");
    }

    @Override
    public void deleteAll(Class entityClass) {
        executeTransactionalJPAOperation(em -> {
            em.createQuery("delete from " + entityClass.getSimpleName()).executeUpdate();
            return null;
        }, "Could not delete all entries of" + entityClass.getSimpleName());
    }
}
