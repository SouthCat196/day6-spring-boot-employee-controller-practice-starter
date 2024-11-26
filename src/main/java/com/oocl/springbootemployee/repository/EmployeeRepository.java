package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.EmployeeDto;
import com.oocl.springbootemployee.model.Gender;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.oocl.springbootemployee.constant.EmployeeConstant.ONE;

@Repository
public class EmployeeRepository {
    private final List<Employee> employeeList = new ArrayList<>();

    private int sequence = ONE;

    public List<Employee> getAll() {
        return employeeList;
    }

    public Employee getById(int id) {
        return employeeList.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Employee> getByGender(Gender gender) {
        return employeeList.stream()
                .filter(employee -> employee.getGender().toString().equals(gender.toString()))
                .collect(Collectors.toList());
    }

    public Employee createEmployee(Employee employee) {
        employee.setId(sequence);
        sequence += ONE;
        employeeList.add(employee);
        return employee;
    }

    public Employee updateEmployee(Integer id, EmployeeDto employeeDto) {
        Employee employee = getById(id);
        employee.setAge(employeeDto.getAge());
        employee.setSalary(employeeDto.getSalary());
        return employee;
    }

    public void deleteEmployee(Integer id) {
        employeeList.removeIf(employee -> employee.getId().equals(id));
    }

    public void resetSequence() {
        sequence = ONE;
    }

    public List<Employee> getPageEmployee(int page, int size) {
        return employeeList.stream()
                .skip((long) (page - 1) * size)
                .limit(size)
                .toList();
    }
}
