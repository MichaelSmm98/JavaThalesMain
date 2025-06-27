package com.michaelsmm.JavaThales.handler.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.michaelsmm.JavaThales.models.ApiResponse;
import com.michaelsmm.JavaThales.models.Employee;
import com.michaelsmm.JavaThales.usecase.ComputeAnualSalaryEmployee;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeApiClient {
    public Mono<List<Employee>> fetchEmployees(String url) {
        return Mono.fromCallable(() -> {
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpGet request = new HttpGet(url);
                String jsonResponse;

                try (var response = httpClient.execute(request)) {
                    HttpEntity entity = response.getEntity();
                    jsonResponse = EntityUtils.toString(entity);
                }

                Gson gson = new Gson();
                ApiResponse apiResponse = gson.fromJson(jsonResponse, ApiResponse.class);
                for (Employee emp : apiResponse.getData()) {
                    ComputeAnualSalaryEmployee.calculateAnnualSalary(emp);
                    System.out.println(emp.getId() + " - " + emp.getEmployee_name() + " ($" + emp.getEmployee_salary() + ")" + "Anual Salary: " + emp.getEmployeeAnnualSalary());
                }
                return apiResponse.getData();

            } catch (IOException e) {
                e.printStackTrace();
                return Collections.emptyList();
            }
        });
    }

    public Mono<Employee> fetchEmployeeById(String id) {
        return Mono.fromCallable(() -> {
            try (var httpClient = HttpClients.createDefault()) {
                var request = new HttpGet("https://dummy.restapiexample.com/api/v1/employee/" + id);
                var response = httpClient.execute(request);
                var json = EntityUtils.toString(response.getEntity());

                var gson = new Gson();
                var jsonObject = gson.fromJson(json, JsonObject.class);

                if (jsonObject.has("data") && !jsonObject.get("data").isJsonNull()) {
                    return gson.fromJson(jsonObject.get("data"), Employee.class);
                }

                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

}
