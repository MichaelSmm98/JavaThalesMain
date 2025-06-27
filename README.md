This project is a Java-based MVC web application developed using Spring Boot and built to demonstrate layered architecture.

The application is packaged as a WAR file and is deployable on Spring Boot embedded server.

Backend Framework: Spring Boot (MVC)

JDK Version: Java 1.8+

Container Compatibility: Spring Boot embedded server & Wildfly

REST API Integration: Consumes data from:

http://dummy.restapiexample.com/api/v1/employees

http://dummy.restapiexample.com/api/v1/employee/{id}

Testing: JUnit 5 for Business Layer
Tests verify the correctness of the annual salary computation logic.

Fetches all employees: /employees

Fetch single employee by ID: /employee/{id}

In order to use this api you must use

- http://localhost:8080/employees to get all employees
- http://localhost:8080/employees/1 To get the employee #1

The structure used:

src/main/java/com.michaelsmm.JavaThales

│

├── handler/

│ └── api/

│ └── EmployeeHandler.java

│

├── models/

│ ├── ApiResponse.java 

│ └── Employee.java 

│

├── usecase/

│ └── ComputeAnualSalaryEmployee.java
│

├── CorsConfig.java # Configuración de CORS

├── JavaThalesApplication.java

└── Router.java




