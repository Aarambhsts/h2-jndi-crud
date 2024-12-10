package com.unisys.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class H2HealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "")) {
            if (connection.isValid(1000)) {
                return Health.up().withDetail("H2", "Running").build();
            }
        } catch (Exception e) {
            return Health.down(e).withDetail("H2", "Not reachable").build();
        }
        return Health.unknown().build();
    }
}
