package com.michaelsmm.JavaThales.usecase;

import com.michaelsmm.JavaThales.models.Employee;

public class ComputeAnualSalaryEmployee {

    public static void calculateAnnualSalary(Employee employee) {
        int employeeAnualSalary = 0;

        employeeAnualSalary = employee.getEmployeeSalary()*12;
        employee.setEmployeeAnualSalary(employeeAnualSalary);
    }

}
