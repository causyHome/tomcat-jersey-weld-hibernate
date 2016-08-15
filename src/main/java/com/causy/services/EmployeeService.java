package com.causy.services;

import com.causy.model.Employee;

public interface EmployeeService {

    int create(Employee newEmployee);

    Employee get(int employeeId);
}
