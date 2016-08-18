package com.causy.services;

import org.hibernate.Session;

@FunctionalInterface
interface HibernateOperationStrategy {
    Object execute(Session session);
}
