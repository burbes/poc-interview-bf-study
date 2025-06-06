package com.example.taskmanagement.config;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.log4j.Log4j2;

import java.util.Optional;

/**
 * Database configuration for the application.
 * Sets up Flyway migration, JPA repositories, and transaction management.
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.example.taskmanagement.repository")
@EnableTransactionManagement
@Log4j2
public class DatabaseConfig {

    /**
     * Custom Flyway migration strategy for development profile.
     * In development, we can clean the database before migration if needed.
     */
    @Bean
    @Profile("dev")
    public FlywayMigrationStrategy flywayMigrationStrategyForDev() {
        return flyway -> {
            // Uncomment to enable database cleaning in development
            // flyway.clean();
            flyway.migrate();
            log.info("Flyway migration executed in development environment");
        };
    }

    /**
     * Custom Flyway migration strategy for production profile.
     * In production, we only perform migration without cleaning.
     */
    @Bean
    @Profile("prod")
    public FlywayMigrationStrategy flywayMigrationStrategyForProd() {
        return flyway -> {
            flyway.migrate();
            log.info("Flyway migration executed in production environment");
        };
    }

    /**
     * Auditor aware implementation for entity auditing.
     * This will be updated to use actual authenticated user when security is fully implemented.
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        // This is a placeholder. In a real application, this would return the current authenticated user
        return () -> Optional.of("system");
    }
}

