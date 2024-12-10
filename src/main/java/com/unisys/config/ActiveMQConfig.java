package com.unisys.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.connection.CachingConnectionFactory;

import jakarta.jms.ConnectionFactory;

@Configuration
@EnableJms
public class ActiveMQConfig {

    // Define the ConnectionFactory bean
    @Bean
    public ConnectionFactory connectionFactory() {
        // Create an ActiveMQConnectionFactory instance and provide the broker URL
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        // Optionally, wrap it with CachingConnectionFactory to improve performance
        return new CachingConnectionFactory(activeMQConnectionFactory);
    }

    // Define the JmsTemplate bean
    @Bean
    public JmsTemplate jmsTemplate(jakarta.jms.ConnectionFactory connectionFactory) {
        // Return the JmsTemplate using the provided connectionFactory
        return new JmsTemplate(connectionFactory);
    }
}
