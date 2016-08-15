package com.causy.services;

import com.causy.model.Employee;

import java.util.List;

public interface EmployeeService {

    int create(Employee newEmployee);

    Employee get(int employeeId);

    void update(Employee employee);

    List<Employee> list();
}
