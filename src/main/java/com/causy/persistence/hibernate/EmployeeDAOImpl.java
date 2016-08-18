package com.causy.persistence.hibernate;

import com.causy.model.Employee;
import com.causy.persistence.dao.EmployeeDAO;
import com.causy.persistence.hibernate.SessionFactoryManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    private final SessionFactoryManager sessionFactoryManager;

    EmployeeDAOImpl() {
        this.sessionFactoryManager = SessionFactoryManager.instance;
    }

    EmployeeDAOImpl(SessionFactoryManager sessionFactoryManager) {
        this.sessionFactoryManager = sessionFactoryManager;
    }

    @Override
    public int create(Employee newEmployee) {

        return (int) performHibernateOperation(session -> session.save(newEmployee), "Could not create entity!");
    }

    @Override
    public Employee get(int employeeId) {
        return (Employee) performHibernateOperation(session -> session.get(Employee.class, employeeId), String.format("Could not find entity with Id %s", employeeId));
    }

    @Override
    public void update(Employee employee) {
        performHibernateOperation(session -> {
            session.update(employee);
            return null;
        }, "Something went wrong when trying to update entity, it probably doesnt exist");
    }

    @Override
    public List<Employee> list() {
        return (List<Employee>) performHibernateOperation(session -> session.createCriteria(Employee.class).list(), "Could not get list of entity");
    }

    @Override
    public int count() {
        return (int) performHibernateOperation(session -> session.createQuery("select count(*) from Employee ").uniqueResult(), "Could not get count of entity");
    }

    private Object performHibernateOperation(final HibernateOperationStrategy operation, String errorMessage) {
        SessionFactory sessionFactory = sessionFactoryManager.getSessionFactory();

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            final Object returnedData = operation.execute(session);
            tx.commit();
            return returnedData;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new IllegalStateException(errorMessage, e);
        } finally {
            session.close();
        }
    }


}
