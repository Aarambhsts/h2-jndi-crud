package com.unisys.controller;

import com.unisys.model.Employee;
import com.unisys.service.EmployeeService;
import com.unisys.service.EmailService;
import com.unisys.exception.EmployeeNotFoundException;
import com.unisys.exception.ValidationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/api/employees")
@Component
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    // Define constants for repeated values
    private static final String EMPLOYEE_EMAIL = "aadia2411@gmail.com";
    private static final String EMPLOYEE_QUEUE = "employeeQueue";
    private static final String EMPLOYEE_ID_MESSAGE = "Employee with ID ";

    private final EmployeeService employeeService;
    private final EmailService emailService;
    private final JmsTemplate jmsTemplate;

    // Constructor injection for dependencies
    public EmployeeController(EmployeeService employeeService, EmailService emailService, JmsTemplate jmsTemplate) {
        this.employeeService = employeeService;
        this.emailService = emailService;
        this.jmsTemplate = jmsTemplate;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEmployee(Employee employee) {
        if (employee == null || employee.getId() == null || employee.getName() == null || employee.getEmail() == null) {
            throw new ValidationException("Employee data is incomplete.");
        }

        // Log the creation of an employee
        logger.info("Creating new employee with ID: {}", employee.getId());

        employeeService.saveEmployee(employee);

        // Send email and message to the queue
        emailService.sendSimpleEmail(
            EMPLOYEE_EMAIL, 
            "New Employee Created", 
            EMPLOYEE_ID_MESSAGE + employee.getId() + " has been created."
        );
        jmsTemplate.convertAndSend(EMPLOYEE_QUEUE, "Employee Created: " + employee.getId());

        return Response.status(Response.Status.CREATED).entity("Employee created successfully.").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployeeById(@PathParam("id") Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            throw new EmployeeNotFoundException(EMPLOYEE_ID_MESSAGE + id + " not found.");
        }

        // Log the retrieval of an employee
        logger.info("Retrieved employee with ID: {}", id);

        return employee;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("id") Long id, Employee employee) {
        if (employee == null) {
            throw new ValidationException("Employee data is missing.");
        }
        employee.setId(id);
        employeeService.updateEmployee(employee);

        // Send email and message to the queue
        emailService.sendSimpleEmail(
            EMPLOYEE_EMAIL, 
            "Employee Updated", 
            EMPLOYEE_ID_MESSAGE + id + " has been updated."
        );
        jmsTemplate.convertAndSend(EMPLOYEE_QUEUE, "Employee Updated: " + id);

        // Log the update of an employee
        logger.info("Updated employee with ID: {}", id);

        return Response.ok("Employee updated successfully.").build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEmployee(@PathParam("id") Long id) {
        Employee existingEmployee = employeeService.getEmployeeById(id);
        if (existingEmployee == null) {
            throw new EmployeeNotFoundException(EMPLOYEE_ID_MESSAGE + id + " not found.");
        }
        employeeService.deleteEmployee(id);

        // Send email and message to the queue
        emailService.sendSimpleEmail(
            EMPLOYEE_EMAIL, 
            "Employee Deleted", 
            EMPLOYEE_ID_MESSAGE + id + " has been deleted."
        );
        jmsTemplate.convertAndSend(EMPLOYEE_QUEUE, "Employee Deleted: " + id);

        // Log the deletion of an employee
        logger.info("Deleted employee with ID: {}", id);

        return Response.ok("Employee deleted successfully.").build();
    }
}
