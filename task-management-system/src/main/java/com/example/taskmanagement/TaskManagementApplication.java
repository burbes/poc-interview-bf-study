package com.example.taskmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.log4j.Log4j2;

/**
 * Main application class for the Task Management System.
 * This initializes the Spring Boot application and enables necessary features.
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
@Log4j2
public class TaskManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagementApplication.class, args);
        log.info("Task Management System started successfully");
    }
}

