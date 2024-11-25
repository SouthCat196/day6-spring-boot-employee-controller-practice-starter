package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class EmployeeControllerTest {

    @Autowired
    private MockMvc client;

    @Autowired
    private JacksonTester<Employee> employeeJacksonTester;

    @Autowired
    private JacksonTester<List<Employee>> employeesListJacksonTester;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employeeRepository.getAll().clear();
        employeeRepository.createEmployee(new Employee(0, "Tom", 20, Gender.FEMALE, 8000.0));
        employeeRepository.createEmployee(new Employee(0, "Amy", 15, Gender.FEMALE, 7000.0));
        employeeRepository.createEmployee(new Employee(0, "Ben", 19, Gender.MALE, 5000.0));
    }

    @Test
    void should_return_employees_when_get_all_given_emplpyees() throws Exception {
        // Given
        final List<Employee> employeeList = employeeRepository.getAll();

        String employeeListJson = employeesListJacksonTester.write(employeeList).getJson();

        // When & Then
        client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.content().json(employeeListJson));

    }

    @Test
    void should_return_employee_when_get_employee_by_id() throws Exception {
        // Given
        final Employee employee = employeeRepository.getById(3);
        int id = employee.getId();

        String employeeJson = employeeJacksonTester.write(employee).getJson();

        // When & Then
        client.perform(MockMvcRequestBuilders.get("/employees/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(employeeJson));

    }

    @Test
    void should_return_male_when_get_by_male_given_gender_is_male() throws Exception {
        // Given
        final List<Employee> employeeList = employeeRepository.getByGender(Gender.MALE);
        String employeeListJson = employeesListJacksonTester.write(employeeList).getJson();

        // When & Then
        client.perform(MockMvcRequestBuilders.get("/employees")
                        .param(("gender"), "MALE")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(employeeListJson));
    }

    @Test
    void should_return_created_employee_when_create_employee() throws Exception {
        // Given
        String employeeBody = "    {\n" +
                "        \"name\": \"tom\",\n" +
                "        \"age\": 20,\n" +
                "        \"gender\": \"FEMALE\",\n" +
                "        \"salary\": 8000.0\n" +
                "    }";

        // When & Then
        client.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeBody)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @Test
    void should_update_employee_when_update_employee() throws Exception {
        // Given
        int id = 1;
        Employee employee = employeeRepository.getById(id);
        employee.setAge(123123);
        employee.setSalary(321321321.0);
        String employeeJson = employeeJacksonTester.write(employee).getJson();

        // When & Then
        client.perform(MockMvcRequestBuilders.put("/employees/{id}", employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(employeeJson));
    }

    @Test
    void should_delete_and_return_204_status_when_delete_employee() throws Exception {
        // Given
        int id = 2;

        // When & Then
        client.perform(MockMvcRequestBuilders.delete("/employees/" + id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}

