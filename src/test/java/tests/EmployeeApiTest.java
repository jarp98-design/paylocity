package tests;

import io.restassured.response.Response;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;

import utils.RequestFactory;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import static org.hamcrest.Matchers.*;

public class EmployeeApiTest {

    private static UUID employeeId;
    private static String username = "TestUser867";
    private static Map<String, Object> newEmployee = new HashMap<>();

    static {
        newEmployee.put("firstName", "apifirstname");
        newEmployee.put("lastName", "apilastname");
        newEmployee.put("username", username);
        newEmployee.put("dependants", 2);
    }

    @BeforeClass
    public void setup() {
        RestAssured.requestSpecification = RequestFactory.getRequestSpec();
    }

    @Test(priority = 1)
    public void createEmployee() {
        Response response =
            given()
                .body(newEmployee)
            .when()
                .post("/api/Employees")
            .then()
                .statusCode(200)
                .extract()
                .response();

        employeeId = response.jsonPath().getUUID("id");
    }

    @Test(priority = 2)
    public void getAllEmployees() {
        when()
            .get("/api/Employees")
        .then()
            .statusCode(200)
            .body("firstName", hasItem("Steve"))
            .body("lastName", hasItem("Rogers"));
        
        // Verify the newly created employee is in the list
        when()
            .get("/api/Employees")
        .then()
            .statusCode(200)
            .body("firstName", hasItem(newEmployee.get("firstName")))
            .body("lastName", hasItem(newEmployee.get("lastName")))
            .body("dependants", hasItem(newEmployee.get("dependants")));
    }

    @Test(priority = 3)
    public void getEmployeeById() {
        String steveId = "d4fc695c-cdea-4a80-b719-be5a88613405";

        when()
            .get("/api/Employees/{id}", steveId)
        .then()
            .statusCode(200)
            .body("id", equalTo(steveId))
            .body("firstName", equalTo("Steve"))
            .body("lastName", equalTo("Rogers"));

        // Verify the newly created employee can be retrieved by ID
        when()
            .get("/api/Employees/{id}", employeeId)
        .then()
            .statusCode(200)
            .body("id", equalTo(employeeId.toString()))
            .body("firstName", equalTo(newEmployee.get("firstName")))
            .body("lastName", equalTo(newEmployee.get("lastName")));
    }

    @Test(priority = 4)
    public void updateEmployee() {
        Map<String, Object> updateEmployee = new HashMap<>();
        updateEmployee.put("id", employeeId.toString());
        updateEmployee.put("username", username);
        updateEmployee.put("firstName", "apifirstnameUpdated");
        updateEmployee.put("lastName", "apilastnameUpdated");
        updateEmployee.put("dependants", 5);

        given()
            .body(updateEmployee)
        .when()
            .put("/api/Employees")
        .then()
            .statusCode(200);

        // Verify the employee has been updated
        when()
            .get("/api/Employees/{id}", employeeId)
        .then()
            .statusCode(200)
            .body("id", equalTo(employeeId.toString()))
            .body("firstName", equalTo(updateEmployee.get("firstName")))
            .body("lastName", equalTo(updateEmployee.get("lastName")))
            .body("dependants", equalTo(updateEmployee.get("dependants")));
    }

    @Test(priority = 5)
    public void deleteEmployee() {
        when()
            .delete("/api/Employees/{id}", employeeId)
        .then()
            .statusCode(200);

        // Verify the employee has been deleted
        when()
            .get("/api/Employees")
        .then()
            .body("id", not(hasItem(employeeId.toString())));
    }
}