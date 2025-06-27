package com.michaelsmm.JavaThales.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ApiResponse {
    private String status;
    private List<Employee> data;
    private String message;
}
