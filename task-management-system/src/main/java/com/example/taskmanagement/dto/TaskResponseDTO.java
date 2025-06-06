package com.example.taskmanagement.dto;

import com.example.taskmanagement.model.Task;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for task response data.
 * Used for returning task information through the API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Task API response DTO")
public class TaskResponseDTO {

    @Schema(description = "Task ID", example = "1")
    private Long id;
    
    @Schema(description = "Task title", example = "Implement login")
    private String title;
    
    @Schema(description = "Task description", example = "Implement user login functionality")
    private String description;
    
    @Schema(description = "Task status", example = "TODO")
    private Task.TaskStatus status;
    
    @Schema(description = "Task priority", example = "MEDIUM")
    private Task.TaskPriority priority;
    
    @Schema(description = "Task due date", example = "2024-06-30T23:59:59")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dueDate;
    
    @Schema(description = "Creation timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    
    @Schema(description = "Last update timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
    
    @Schema(description = "Project ID", example = "1")
    private Long projectId;
    
    @Schema(description = "Assignee user ID", example = "2")
    private Long assigneeId;
    
    @Schema(description = "Assignee full name", example = "John Doe")
    private String assigneeName;

    /**
     * Convert Task entity to ResponseDTO.
     *
     * @param task Task entity
     * @return TaskResponseDTO
     */
    public static TaskResponseDTO fromEntity(Task task) {
        if (task == null) {
            return null;
        }
        
        TaskResponseDTO dto = TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();

        // Set project ID if available
        if (task.getProject() != null) {
            dto.setProjectId(task.getProject().getId());
        }

        // Set assignee information if available
        if (task.getAssignee() != null) {
            dto.setAssigneeId(task.getAssignee().getId());
            dto.setAssigneeName(task.getAssignee().getFullName());
        }

        return dto;
    }
}
