package com.causy.persistence.dao;


import java.util.List;

public interface BasicDAO {
    Object create(Object newEntity);

    Object get(Class entityClass, int entityId);

    Object update(Object entity);

    List list(Class entityClass);

    long count(Class entityClass);

    void deleteAll(Class entityClass);
}
