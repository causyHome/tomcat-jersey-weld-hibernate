package com.causy.persistence;

import com.causy.model.Employee;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Test;

public class HibernateSetupTest {
    @Test
    public void should_be_able_to_write_entity_and_then_read_it() {

        SessionFactory sessionFactory = SessionFactoryManager.instance.getSessionFactory();

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.save(new Employee(1, "Thomas", "test"));
        tx.commit();

        session = sessionFactory.getCurrentSession();
        tx = session.beginTransaction();
        final Employee actual = (Employee) session.get(Employee.class, 1);
        tx.commit();

        Assertions.assertThat(actual.getName()).isEqualTo("Thomas");
        Assertions.assertThat(actual.getRole()).isEqualTo("test2");
    }
}
