package com.example.taskmanagement.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.data.domain.AuditorAware;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConfigTest {
    private final DatabaseConfig config = new DatabaseConfig();

    @Test
    void testAuditorProvider() {
        AuditorAware<String> auditor = config.auditorProvider();
        java.util.Optional<String> result = auditor.getCurrentAuditor();
        assertTrue(result.isPresent());
        assertEquals("system", result.get());
    }

    @Test
    void testFlywayMigrationStrategyForDev() {
        FlywayMigrationStrategy strategy = config.flywayMigrationStrategyForDev();
        assertNotNull(strategy);
    }

    @Test
    void testFlywayMigrationStrategyForProd() {
        FlywayMigrationStrategy strategy = config.flywayMigrationStrategyForProd();
        assertNotNull(strategy);
    }
} 