package com.unisys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;


import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
	
    public DataSource dataSource() throws NamingException  {
        InitialContext initialContext = new InitialContext();
        // Ensure this matches the resource name you defined in TomcatConfiguration
       return (DataSource) initialContext.lookup("jdbc/h2ds");
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
