package com.causy.persistence.hibernate;

import org.hibernate.Session;

@FunctionalInterface
interface HibernateTransactionalOperation {
    Object execute(Session session);
}
