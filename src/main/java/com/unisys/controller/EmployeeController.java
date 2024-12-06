package com.unisys.controller;

import com.unisys.service.EmployeeService;
import com.unisys.service.EmailService;
import com.unisys.model.Employee;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controller class that handles HTTP requests related to Employee resources.
 * This class provides CRUD operations for Employee objects through the EmployeeService.
 */
@Path("/api/employees")
@Component // Marks this class as a Spring-managed bean
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmailService emailService;
    private final JmsTemplate jmsTemplate; // Injected JmsTemplate

    // Constructor for dependency injection
    public EmployeeController(EmployeeService employeeService, EmailService emailService, JmsTemplate jmsTemplate) {
        this.employeeService = employeeService;
        this.emailService = emailService;
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * Handles the creation of a new Employee.
     *
     * @param employee the Employee object to be created.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createEmployee(Employee employee) {
        employeeService.saveEmployee(employee);

        // Send email notification after creating the employee
        String emailSubject = "New Employee Created";
        String emailText = "An employee with ID " + employee.getId() + " has been created.";
        emailService.sendSimpleEmail("aadia2411@gmail.com", emailSubject, emailText);

        // Send a message to the queue after creating the employee
        jmsTemplate.convertAndSend("employeeQueue", "Employee Created: " + employee.getId());
    }

    /**
     * Retrieves all Employees from the database.
     *
     * @return a list of all Employees.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    /**
     * Retrieves a specific Employee by their ID.
     *
     * @param id the ID of the Employee to retrieve.
     * @return the Employee with the specified ID.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployeeById(@PathParam("id") Long id) {
        return employeeService.getEmployeeById(id);
    }

    /**
     * Updates an existing Employee's information.
     *
     * @param id       the ID of the Employee to update.
     * @param employee the updated Employee object.
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateEmployee(@PathParam("id") Long id, Employee employee) {
        employee.setId(id);
        employeeService.updateEmployee(employee);

        // Send email notification after updating the employee
        String emailSubject = "Employee Updated";
        String emailText = "Employee with ID " + id + " has been updated.";
        emailService.sendSimpleEmail("aadia2411@gmail.com", emailSubject, emailText);

        // Send a message to the queue after updating the employee
        jmsTemplate.convertAndSend("employeeQueue", "Employee Updated: " + id);
    }

    /**
     * Deletes an Employee by their ID.
     *
     * @param id the ID of the Employee to delete.
     */
    @DELETE
    @Path("/{id}")
    public void deleteEmployee(@PathParam("id") Long id) {
        employeeService.deleteEmployee(id);

        // Send email notification after deleting the employee
        String emailSubject = "Employee Deleted";
        String emailText = "Employee with ID " + id + " has been deleted.";
        emailService.sendSimpleEmail("aadia2411@gmail.com", emailSubject, emailText);

        // Send a message to the queue after deleting the employee
        jmsTemplate.convertAndSend("employeeQueue", "Employee Deleted: " + id);
    }
}
