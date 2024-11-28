package com.example.sample;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeDAO employeeDAO;

    @PostMapping
    public void createEmployee(@RequestBody Employee employee) {
        employeeDAO.save(employee);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeDAO.findById(id);
    }

    @PutMapping("/{id}")
    public void updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setId(id);
        employeeDAO.update(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeDAO.deleteById(id);
    }
}
