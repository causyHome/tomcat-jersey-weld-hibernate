package com.causy.persistence.hibernate;

import javax.persistence.EntityManager;

@FunctionalInterface
interface JPATransactionalOperation {
    Object execute(EntityManager entityManager);
}
