package com.michaelsmm.JavaThales.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Employee {
    private int id;
    private String employeeName;
    private int employeeSalary;
    private int employeeAge;
    private String profileImage;
    private int employeeAnualSalary;
}
