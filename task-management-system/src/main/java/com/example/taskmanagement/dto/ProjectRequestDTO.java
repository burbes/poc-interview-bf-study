package com.example.taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Set;

@Data
@Schema(description = "Project creation/update request DTO")
public class ProjectRequestDTO {
    @NotBlank
    @Size(min = 3, max = 100)
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
    @Schema(description = "IDs of project members", example = "[1,2,3]")
    private Set<Long> memberIds;
} 