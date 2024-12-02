package com.unisys.dao;

import com.unisys.model.Employee;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for performing CRUD operations on the Employee entity.
 * This class interacts with the database using JDBC to persist and retrieve Employee objects.
 */
@Repository
public class EmployeeDAO {

    private final DataSource dataSource;

    /**
     * Constructor that initializes the EmployeeDAO with the provided DataSource.
     *
     * @param dataSource the DataSource used to get database connections.
     */
    public EmployeeDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Saves the given Employee object to the database.
     * 
     * @param employee the Employee object to be saved.
     */
    public void save(Employee employee) {
        String sql = "INSERT INTO EMPLOYEE (ID, NAME, EMAIL) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, employee.getId());
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving employee: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves all Employee records from the database.
     *
     * @return a list of all Employees in the database.
     */
    public List<Employee> findAll() {
        String sql = "SELECT * FROM EMPLOYEE";
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("ID"));
                employee.setName(resultSet.getString("NAME"));
                employee.setEmail(resultSet.getString("EMAIL"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching employees: " + e.getMessage(), e);
        }
        return employees;
    }

    /**
     * Retrieves an Employee by their ID from the database.
     *
     * @param id the ID of the Employee to be retrieved.
     * @return the Employee with the specified ID, or null if not found.
     */
    public Employee findById(Long id) {
        String sql = "SELECT * FROM EMPLOYEE WHERE ID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.setId(resultSet.getLong("ID"));
                    employee.setName(resultSet.getString("NAME"));
                    employee.setEmail(resultSet.getString("EMAIL"));
                    return employee;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding employee by ID: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Updates the details of an existing Employee in the database.
     * 
     * @param employee the Employee object containing the updated details.
     */
    public void update(Employee employee) {
        String sql = "UPDATE EMPLOYEE SET NAME = ?, EMAIL = ? WHERE ID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setLong(3, employee.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating employee: " + e.getMessage(), e);
        }
    }

    /**
     * Deletes the Employee with the specified ID from the database.
     * 
     * @param id the ID of the Employee to be deleted.
     */
    public void deleteById(Long id) {
        String sql = "DELETE FROM EMPLOYEE WHERE ID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting employee by ID: " + e.getMessage(), e);
        }
    }
}
