package com.causy.persistence.impl;

import javax.persistence.EntityManager;

@FunctionalInterface
interface JPATransactionalOperation {
    Object execute(EntityManager entityManager);
}
