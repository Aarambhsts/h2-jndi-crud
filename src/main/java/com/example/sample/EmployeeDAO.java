package com.example.sample;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAO {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Employee employee) {
        String sql = "INSERT INTO EMPLOYEE (ID, NAME, EMAIL) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, employee.getId(), employee.getName(), employee.getEmail());
    }

    public List<Employee> findAll() {
        String sql = "SELECT * FROM EMPLOYEE";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employee.class));
    }

    public Employee findById(Long id) {
        String sql = "SELECT * FROM EMPLOYEE WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Employee.class), id);
    }

    public void update(Employee employee) {
        String sql = "UPDATE EMPLOYEE SET NAME = ?, EMAIL = ? WHERE ID = ?";
        jdbcTemplate.update(sql, employee.getName(), employee.getEmail(), employee.getId());
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM EMPLOYEE WHERE ID = ?";
        jdbcTemplate.update(sql, id);
    }
}
