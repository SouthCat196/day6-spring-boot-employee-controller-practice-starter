package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.EmployeeDto;
import com.oocl.springbootemployee.model.Gender;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private final List<Employee> employeeList = new ArrayList<>();

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
        employee.setId(employeeList.stream()
                .mapToInt(Employee::getId)
                .max()
                .orElse(0) + 1);
        employeeList.add(employee);
        return employee;
    }

    public Employee updateEmployee(Integer id, EmployeeDto employeeDto) {
        Optional<Employee> employee = employeeList.stream()
                .filter(employeeItem -> employeeItem.getId().equals(id))
                .findFirst();
        if (employee.isPresent()) {
            employee.get().setAge(employeeDto.getAge());
            employee.get().setSalary(employeeDto.getSalary());
            return employee.get();
        }
        return null;
    }

    public void deleteEmployee(Integer id) {
        employeeList.removeIf(employee -> employee.getId().equals(id));
    }

    public List<Employee> getPageEmployee(int page, int size) {
        return employeeList.stream()
                .skip((long) (page - 1) * size)
                .limit(size)
                .toList();
    }
}
