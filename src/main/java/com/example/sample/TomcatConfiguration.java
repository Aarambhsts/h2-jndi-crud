package com.example.sample;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.deploy.NamingResourcesImpl;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
public class TomcatConfiguration {
    
//    @Bean
//    public TomcatServletWebServerFactory tomcatFactory() {
//        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
//        factory.addContextCustomizers(new TomcatContextCustomizer() {
//            @Override
//            public void customize(Context context) {
//                context.getNamingResources().addResource(createJndiResource());
//            }
//        });
//        return factory;
//    }
	
	@Bean
	public TomcatServletWebServerFactory servletContainer() {
		
		return new TomcatServletWebServerFactory() {
 
			/**
			 * Applying additional processing to the Tomcat server.
			 * @param tomcat the Tomcat server.
			 * @return TomcatWebServer instance
			 */
            @Override
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                //step 1 enable naming
                tomcat.enableNaming();
                return super.getTomcatWebServer(tomcat);
            }
 
        	/**
        	 * Creates and configures a resources for Tomcat Server
        	 */
            @Override
            protected void postProcessContext(Context context) {
                //step 2 create resource
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

    private ContextResource createJndiResource() {
        ContextResource resource = new ContextResource();
        resource.setName("jdbc/h2ds");
        resource.setType("javax.sql.DataSource");
        resource.setProperty("factory", "com.zaxxer.hikari.HikariJNDIFactory");
        resource.setProperty("driverClassName", "org.h2.Driver");
        resource.setProperty("jdbcUrl", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        resource.setProperty("username", "sa");
        resource.setProperty("password", "");
        return resource;
    }
    
    @Bean
    @Primary
    public DataSource getDataSource() throws IllegalArgumentException, NamingException{
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("java:comp/env/jdbc/h2ds");
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(false);
        bean.afterPropertiesSet();
        return (DataSource) bean.getObject();
    }
}