package com.causy.services;

import com.causy.model.Employee;
import com.causy.persistence.SessionFactoryManager;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public int create(Employee newEmployee) {
        SessionFactory sessionFactory = SessionFactoryManager.instance.getSessionFactory();

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.save(newEmployee);
        tx.commit();
        return newEmployee.getId();
    }

    @Override
    public Employee get(int employeeId) {
        SessionFactory sessionFactory = SessionFactoryManager.instance.getSessionFactory();

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Employee e = (Employee) session.get(Employee.class, employeeId);
        tx.commit();
        return e;
    }

    @Override
    public List<Employee> list(){
        SessionFactory sessionFactory = SessionFactoryManager.instance.getSessionFactory();

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        final List<Employee> employees = session.createCriteria(Employee.class).list();
        tx.commit();
        return employees;
    }
}
