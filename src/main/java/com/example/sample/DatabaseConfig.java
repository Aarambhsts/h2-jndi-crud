package com.example.sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import jakarta.ws.rs.Path;

import javax.naming.InitialContext;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
	
    public DataSource dataSource() throws Exception {
        InitialContext initialContext = new InitialContext();
        // Ensure this matches the resource name you defined in TomcatConfiguration
       return (DataSource) initialContext.lookup("jdbc/h2ds");
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
