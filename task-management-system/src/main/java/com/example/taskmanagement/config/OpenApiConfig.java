package com.example.taskmanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

/**
 * OpenAPI (Swagger) configuration for API documentation.
 */
@Configuration
public class OpenApiConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * Configures the OpenAPI documentation for the application.
     */
    @Bean
    public OpenAPI openAPI() {
        final String securitySchemeName = "bearerAuth";
        
        return new OpenAPI()
            .info(new Info()
                .title(applicationName + " API")
                .description("RESTful API for the Task Management System")
                .version("v1.0.0")
                .contact(new Contact()
                    .name("Development Team")
                    .email("dev@example.com")
                    .url("https://example.com"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
            .servers(List.of(
                new Server().url("/").description("Default Server URL")))
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .components(new Components()
                .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                    .name(securitySchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .description("JWT Authorization header using Bearer scheme")));
    }
}

