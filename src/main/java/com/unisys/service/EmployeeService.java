package com.unisys.service;

import com.unisys.dao.EmployeeDAO;
import com.unisys.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that handles the business logic for Employee operations.
 * This class communicates with the EmployeeDAO to perform CRUD operations on Employee entities.
 */
@Service
public class EmployeeService {

    private final EmployeeDAO employeeDAO;

    /**
     * Constructor that initializes the EmployeeService with the provided EmployeeDAO.
     *
     * @param employeeDAO the DAO used to interact with the database.
     */
    public EmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    /**
     * Saves a new Employee to the database.
     * 
     * @param employee the Employee object to be saved.
     */
    public void saveEmployee(Employee employee) {
        employeeDAO.save(employee);
    }

    /**
     * Retrieves all Employees from the database.
     * 
     * @return a list of all Employees.
     */
    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }

    /**
     * Retrieves a specific Employee by their ID from the database.
     * 
     * @param id the ID of the Employee to retrieve.
     * @return the Employee with the specified ID.
     */
    public Employee getEmployeeById(Long id) {
        return employeeDAO.findById(id);
    }

    /**
     * Updates an existing Employee's details in the database.
     * 
     * @param employee the updated Employee object.
     */
    public void updateEmployee(Employee employee) {
        employeeDAO.update(employee);
    }

    /**
     * Deletes an Employee by their ID from the database.
     * 
     * @param id the ID of the Employee to delete.
     */
    public void deleteEmployee(Long id) {
        employeeDAO.deleteById(id);
    }
}
