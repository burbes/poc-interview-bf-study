package com.example.taskmanagement.controller;

import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.service.TaskService;
import com.example.taskmanagement.dto.TaskRequestDTO;
import com.example.taskmanagement.dto.TaskResponseDTO;
import com.example.taskmanagement.model.Project;
import com.example.taskmanagement.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Task management endpoints")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    @Operation(summary = "Get all tasks")
    public List<TaskResponseDTO> getAllTasks() {
        return taskService.listTasks().stream().map(this::toResponseDTO).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by ID")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponseDTO(taskService.getTaskById(id)));
    }

    @GetMapping("/project/{projectId}")
    @Operation(summary = "Get tasks by project ID")
    public List<TaskResponseDTO> getTasksByProject(@PathVariable Long projectId) {
        return taskService.listTasksByProject(projectId).stream().map(this::toResponseDTO).toList();
    }

    @GetMapping("/assignee/{assigneeId}")
    @Operation(summary = "Get tasks by assignee ID")
    public List<TaskResponseDTO> getTasksByAssignee(@PathVariable Long assigneeId) {
        return taskService.listTasksByAssignee(assigneeId).stream().map(this::toResponseDTO).toList();
    }

    @PostMapping
    @Operation(summary = "Create a new task")
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody @Valid TaskRequestDTO dto) {
        Task task = fromRequestDTO(dto);
        return ResponseEntity.ok(toResponseDTO(taskService.createTask(task)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a task")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @RequestBody @Valid TaskRequestDTO dto) {
        Task updated = fromRequestDTO(dto);
        return ResponseEntity.ok(toResponseDTO(taskService.updateTask(id, updated)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{taskId}/assign/{userId}")
    @Operation(summary = "Assign a task to a user")
    public ResponseEntity<Task> assignTask(@PathVariable Long taskId, @PathVariable Long userId) {
        return ResponseEntity.ok(taskService.assignTask(taskId, userId));
    }

    @PatchMapping("/{taskId}/status")
    @Operation(summary = "Update task status")
    public ResponseEntity<Task> updateStatus(@PathVariable Long taskId, @RequestParam Task.TaskStatus status) {
        return ResponseEntity.ok(taskService.updateStatus(taskId, status));
    }

    @PatchMapping("/{taskId}/priority")
    @Operation(summary = "Update task priority")
    public ResponseEntity<Task> updatePriority(@PathVariable Long taskId, @RequestParam Task.TaskPriority priority) {
        return ResponseEntity.ok(taskService.updatePriority(taskId, priority));
    }

    // --- DTO Mapping ---
    private TaskResponseDTO toResponseDTO(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus() != null ? Task.TaskStatus.valueOf(task.getStatus().name()) : null);
        dto.setPriority(task.getPriority() != null ? Task.TaskPriority.valueOf(task.getPriority().name()) : null);
        dto.setDueDate(task.getDueDate());
        dto.setAssigneeId(task.getAssignee() != null ? task.getAssignee().getId() : null);
        dto.setProjectId(task.getProject() != null ? task.getProject().getId() : null);
        return dto;
    }

    private Task fromRequestDTO(TaskRequestDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        if (dto.getStatus() != null) {
            task.setStatus(dto.getStatus());
        }
        if (dto.getPriority() != null) {
            task.setPriority(dto.getPriority());
        }
        task.setDueDate(dto.getDueDate());
        // Note: assignee and project should be set in the service layer as needed
        return task;
    }
} 