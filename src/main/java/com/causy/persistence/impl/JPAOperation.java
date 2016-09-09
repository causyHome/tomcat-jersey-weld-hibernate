package com.causy.persistence.impl;

import javax.persistence.EntityManager;

@FunctionalInterface
interface JPAOperation {
    Object execute(EntityManager entityManager);
}
