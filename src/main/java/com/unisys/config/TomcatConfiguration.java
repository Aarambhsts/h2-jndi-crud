package com.unisys.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jndi.JndiObjectFactoryBean;

/**
 * Configuration class for Tomcat-specific settings and database configuration.
 * This class provides configuration for embedding Tomcat in the application and
 * setting up a JNDI DataSource for H2 database.
 */
@Configuration
public class TomcatConfiguration {

    /**
     * Configures the Tomcat servlet container with additional settings.
     * Enables naming support for JNDI lookups and configures Tomcat's context.
     *
     * @return a configured TomcatServletWebServerFactory.
     */
    @Bean
    public TomcatServletWebServerFactory servletContainer() {

        return new TomcatServletWebServerFactory() {

            /**
             * Enables naming for JNDI lookups within Tomcat.
             * 
             * @param tomcat the Tomcat server instance to be configured.
             * @return a TomcatWebServer instance.
             */
            @Override
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                tomcat.enableNaming(); // Enable naming for JNDI lookups.
                return super.getTomcatWebServer(tomcat);
            }

            /**
             * Configures the context for the Tomcat server with the specified resource.
             * Creates a new ContextResource for the H2 DataSource and configures its properties.
             * 
             * @param context the Tomcat context to be configured.
             */
            @Override
            protected void postProcessContext(Context context) {
                // Create and configure a ContextResource for the H2 DataSource.
                ContextResource resource = new ContextResource();
                resource.setName("jdbc/h2ds");
                resource.setType("javax.sql.DataSource");
                resource.setProperty("factory", "com.zaxxer.hikari.HikariJNDIFactory");
                resource.setProperty("driverClassName", "org.h2.Driver");
                resource.setProperty("jdbcUrl", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
                resource.setProperty("username", "sa");
                resource.setProperty("password", "");
                context.getNamingResources().addResource(resource);
            }
        };
    }

    /**
     * Creates and configures a JNDI DataSource bean for the application.
     * 
     * @return a configured DataSource bean.
     * @throws IllegalArgumentException if the JNDI name is invalid.
     * @throws NamingException if there is an error looking up the JNDI resource.
     */
    @Bean
    @Primary
    public DataSource getDataSource() throws IllegalArgumentException, NamingException {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("java:comp/env/jdbc/h2ds");
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(false);
        bean.afterPropertiesSet();
        return (DataSource) bean.getObject();
    }
}
 	