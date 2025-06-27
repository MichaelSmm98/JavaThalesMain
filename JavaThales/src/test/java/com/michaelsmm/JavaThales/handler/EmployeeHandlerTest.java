package com.michaelsmm.JavaThales.handler;

import com.michaelsmm.JavaThales.handler.api.EmployeeApiClient;
import com.michaelsmm.JavaThales.models.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeHandlerTest {

    private EmployeeApiClient employeeService;
    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        employeeService = mock(EmployeeApiClient.class);

        EmployeeHandler handler = new EmployeeHandler();
        handler.employeeService = employeeService; // Setter manual

        RouterFunction<ServerResponse> route = RouterFunctions.route()
                .GET("/employees", handler::getEmployees)
                .GET("/employees/{id}", handler::getEmployeeById)
                .build();

        webTestClient = WebTestClient.bindToRouterFunction(route).build();
    }

    @Test
    public void testGetEmployees() {
        List<Employee> mockEmployees = List.of(
                new Employee(1, "John", 50000,23,"",0),
                new Employee(2, "Jane", 60000,32,"",0)
        );

        when(employeeService.fetchEmployees(anyString())).thenReturn(Mono.just(mockEmployees));

        webTestClient.get()
                .uri("/employees")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Employee.class)
                .hasSize(2)
                .consumeWith(response -> {
                    List<Employee> employees = response.getResponseBody();
                    assert employees != null;
                    assert employees.get(0).getEmployeeName().equals("John");
                });
    }

    @Test
    public void testGetEmployeeById_Found() {
        Employee mockEmployee = new Employee(1, "John", 50000,23,"",0);

        when(employeeService.fetchEmployeeById("1")).thenReturn(Mono.just(mockEmployee));

        webTestClient.get()
                .uri("/employees/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Employee.class)
                .consumeWith(response -> {
                    Employee emp = response.getResponseBody();
                    assert emp != null;
                });
    }

    @Test
    public void testGetEmployeeById_NotFound() {
        when(employeeService.fetchEmployeeById("99")).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/employees/99")
                .exchange()
                .expectStatus().isNotFound();
    }
}