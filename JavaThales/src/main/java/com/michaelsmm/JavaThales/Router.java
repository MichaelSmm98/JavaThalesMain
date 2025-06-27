package com.michaelsmm.JavaThales;

import com.michaelsmm.JavaThales.handler.EmployeeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class Router {
    @Bean
    public RouterFunction<ServerResponse> employeeRoutes(EmployeeHandler handler) {
        return RouterFunctions
                .route(GET("/employees"), handler::getEmployees)
                .andRoute(GET("/employees/{id}"), handler::getEmployeeById);

    }
}
