package com.unisys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Configuration class for database-related beans and settings.
 * This class provides the configuration for the DataSource and JdbcTemplate
 * used across the application.
 */
@Configuration
public class DatabaseConfig {

    /**
     * Retrieves the DataSource object configured via JNDI.
     *
     * @return the DataSource object.
     * @throws NamingException if there is an error while performing the JNDI lookup.
     */
    public DataSource dataSource() throws NamingException {
        InitialContext initialContext = new InitialContext();
        // Ensure this matches the resource name you defined in TomcatConfiguration
        return (DataSource) initialContext.lookup("jdbc/h2ds");
    }

    /**
     * Configures a JdbcTemplate bean with the specified DataSource.
     *
     * @param dataSource the DataSource to use for the JdbcTemplate.
     * @return the configured JdbcTemplate instance.
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
