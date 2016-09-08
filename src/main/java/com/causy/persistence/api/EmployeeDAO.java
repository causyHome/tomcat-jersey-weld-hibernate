package com.causy.persistence.api;

import com.causy.model.Employee;

import java.util.List;

public interface EmployeeDAO {
    Employee create(Employee newEmployee);

    Employee get(int employeeId);

    Employee update(Employee employee);

    List<Employee> list();

    long count();

    void delete(int existingEmployee);
}
