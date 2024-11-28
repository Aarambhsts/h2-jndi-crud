package com.unisys;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DatabaseTestController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping("/test")
    public String testDatabaseConnection() {
        try {
            
            List<String> results = jdbcTemplate.queryForList("SELECT 1", String.class);
            return "Database connection successful! Test query result: " + results;
        } catch (Exception e) {
            return "Database connection failed: " + e.getMessage();
        }
    }
}
