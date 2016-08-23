package com.causy.persistence.hibernate;

import com.causy.persistence.dao.BasicDAO;

import java.util.List;

import static com.causy.persistence.hibernate.HibernateUtils.executeTransactionalHibernateOperation;

public class BasicDAOImpl implements BasicDAO {
    @Override
    public int create(Object newEntity) {
        return (int) executeTransactionalHibernateOperation(session -> session.save(newEntity), "Could not create entity!");
    }

    @Override
    public Object get(Class entityClass, int entityId) {
        return executeTransactionalHibernateOperation(session -> session.get(entityClass, entityId), String.format("Could not find entity with Id %s", entityId));
    }

    @Override
    public void update(Object entity) {
        executeTransactionalHibernateOperation(session -> {
            session.update(entity);
            return null;
        }, "Something went wrong when trying to update entity, it probably doesnt exist");
    }

    @Override
    public List<Object> list(Class entityClass) {
        return (List<Object>) executeTransactionalHibernateOperation(session -> session.createCriteria(entityClass).list(), String.format("Could not get list of entity %s", entityClass.getSimpleName()));
    }

    @Override
    public long count(Class entityClass) {
        return (long) executeTransactionalHibernateOperation(session -> session.createQuery("select count(*) from " + entityClass.getSimpleName()).uniqueResult(), "Could not get count of entity");
    }
}
