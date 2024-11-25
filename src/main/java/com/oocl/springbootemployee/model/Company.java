package com.oocl.springbootemployee.model;

import java.util.ArrayList;
import java.util.List;

public class Company {

    private Integer id;

    private String name;

    private final List<Employee> employees = new ArrayList<>();

    public Company(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
