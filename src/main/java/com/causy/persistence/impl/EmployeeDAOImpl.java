package com.causy.persistence.impl;

import com.causy.model.Employee;
import com.causy.model.Team;
import com.causy.persistence.api.BasicDAO;
import com.causy.persistence.api.EmployeeDAO;

import javax.inject.Inject;
import java.util.List;

import static com.causy.persistence.impl.JpaUtils.executeTransactionalJPAOperation;

public class EmployeeDAOImpl implements EmployeeDAO {

    private final BasicDAO basicDAO;

    @Inject
    public EmployeeDAOImpl(BasicDAO basicDAO) {
        this.basicDAO = basicDAO;
    }


    @Override
    public Employee create(Employee newEmployee) {
        basicDAO.create(newEmployee);
        return newEmployee;
    }

    @Override
    public Employee get(int employeeId) {
        return (Employee) basicDAO.get(Employee.class, employeeId);
    }

    @Override
    public Employee update(Employee employee) {
        return (Employee) basicDAO.update(employee);
    }

    @Override
    public List<Employee> list() {
        return (List<Employee>) basicDAO.list(Employee.class);
    }

    @Override
    public long count() {
        return basicDAO.count(Employee.class);
    }

    @Override
    public void delete(int employeeId) {
        executeTransactionalJPAOperation(entityManager -> {
            final Employee employee = entityManager.find(Employee.class, employeeId);
            entityManager.remove(employee);
            return null;
        }, String.format("Could not delete Employee with id %s", employeeId));
    }
}
