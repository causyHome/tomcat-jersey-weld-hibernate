package com.causy.persistence.dao;

import com.causy.model.Employee;

import java.util.List;

public interface EmployeeDAO {

    int create(Employee newEmployee);

    Employee get(int employeeId);

    void update(Employee employee);

    List<Employee> list();

    int count();
}
