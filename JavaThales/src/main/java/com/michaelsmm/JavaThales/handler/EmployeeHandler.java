package com.michaelsmm.JavaThales.handler;

import com.michaelsmm.JavaThales.handler.api.EmployeeApiClient;
import com.michaelsmm.JavaThales.usecase.ComputeAnualSalaryEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class EmployeeHandler {

    @Autowired
    EmployeeApiClient employeeService;

    public Mono<ServerResponse> getEmployees(ServerRequest request) {
        String url = "https://dummy.restapiexample.com/api/v1/employees";
        return employeeService.fetchEmployees(url)
                .flatMap(employees -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(employees));


    }
    public Mono<ServerResponse> getEmployeeById(ServerRequest request) {
        String id = request.pathVariable("id");
        return employeeService.fetchEmployeeById(id)
                .flatMap(employee -> {
                    ComputeAnualSalaryEmployee.calculateAnnualSalary(employee);
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(employee);
                })
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}