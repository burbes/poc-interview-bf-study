package com.example.taskmanagement.dto;

import com.example.taskmanagement.model.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for creating or updating a task.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Task creation/update request DTO")
public class TaskRequestDTO {

    @NotBlank(message = "Task title is required")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    @Schema(description = "Task title", example = "Implement login")
    private String title;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    @Schema(description = "Task description", example = "Implement user login functionality")
    private String description;

    @Schema(description = "Task status", example = "TODO")
    private Task.TaskStatus status;

    @Schema(description = "Task priority", example = "MEDIUM")
    private Task.TaskPriority priority;

    @Future(message = "Due date must be in the future")
    @Schema(description = "Task due date", example = "2024-06-30T23:59:59")
    private LocalDateTime dueDate;

    @NotNull(message = "Project ID is required")
    @Schema(description = "Project ID", example = "1")
    private Long projectId;

    @Schema(description = "Assignee user ID", example = "2")
    private Long assigneeId;

    /**
     * Convert DTO to Task entity.
     *
     * @return Task entity
     */
    public Task toEntity() {
        return Task.builder()
                .title(this.title)
                .description(this.description)
                .status(this.status != null ? this.status : Task.TaskStatus.TODO)
                .priority(this.priority != null ? this.priority : Task.TaskPriority.MEDIUM)
                .dueDate(this.dueDate)
                .build();
    }
}
