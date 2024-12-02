package com.unisys.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.unisys.dao.EmployeeDAO;
import com.unisys.model.Employee;

import java.util.List;

/**
 * Controller class that handles HTTP requests related to Employee resources.
 * This class provides CRUD operations for Employee objects through the EmployeeDAO.
 */
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeDAO employeeDAO;

    /**
     * Handles the creation of a new Employee.
     * 
     * @param employee the Employee object to be created.
     */
    @PostMapping
    public void createEmployee(@RequestBody Employee employee) {
        employeeDAO.save(employee);
    }

    /**
     * Retrieves all Employees from the database.
     * 
     * @return a list of all Employees.
     */
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }

    /**
     * Retrieves a specific Employee by their ID.
     * 
     * @param id the ID of the Employee to retrieve.
     * @return the Employee with the specified ID.
     */
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeDAO.findById(id);
    }

    /**
     * Updates an existing Employee's information.
     * 
     * @param id the ID of the Employee to update.
     * @param employee the updated Employee object.
     */
    @PutMapping("/{id}")
    public void updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setId(id);
        employeeDAO.update(employee);
    }

    /**
     * Deletes an Employee by their ID.
     * 
     * @param id the ID of the Employee to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeDAO.deleteById(id);
    }
}
