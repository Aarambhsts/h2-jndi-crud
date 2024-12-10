package com.unisys.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQHealthIndicator implements HealthIndicator {

    private final JmsTemplate jmsTemplate;

    public ActiveMQHealthIndicator(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public Health health() {
        try {
            // Check if connection to ActiveMQ is healthy
            jmsTemplate.getConnectionFactory().createConnection().start();
            return Health.up().withDetail("ActiveMQ", "Connection successful").build();
        } catch (Exception e) {
            return Health.down().withDetail("ActiveMQ", "Connection failed").build();
        }
    }
}
