package com.causy.persistence.dao;


import java.util.List;

public interface BasicDAO {
    int create(Object newTeam);

    Object get(Class entityClass, int teamId);

    void update(Object team);

    List list(Class entityClass);

    long count(Class entityClass);

    void deleteAll(Class entityClass);
}
