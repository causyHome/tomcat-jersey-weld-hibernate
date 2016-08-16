package com.causy.services;

import org.hibernate.Session;

interface HibernateOperationStrategy {
    Object execute(Session session);
}
