package com.causy.persistence.hibernate;

import org.hibernate.Session;

@FunctionalInterface
interface HibernateOperationStrategy {
    Object execute(Session session);
}
