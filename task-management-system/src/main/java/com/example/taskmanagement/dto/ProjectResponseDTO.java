package com.example.taskmanagement.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Set;

@Data
@Schema(description = "Project API response DTO")
public class ProjectResponseDTO {
    @Schema(description = "Project ID", example = "1")
    private Long id;
    @Schema(description = "Project name", example = "New Project")
    private String name;
    @Schema(description = "Project description", example = "A project for managing tasks")
    private String description;
    @Schema(description = "Project start date", example = "2024-06-01")
    private LocalDate startDate;
    @Schema(description = "Project end date", example = "2024-12-31")
    private LocalDate endDate;
    @Schema(description = "Project status", example = "ACTIVE")
    private String status;
    @Schema(description = "Owner user ID", example = "1")
    private Long ownerId;
    @Schema(description = "IDs of project members", example = "[1,2,3]")
    private Set<Long> memberIds;
    @Schema(description = "IDs of project tasks", example = "[10,11,12]")
    private Set<Long> taskIds;
} 