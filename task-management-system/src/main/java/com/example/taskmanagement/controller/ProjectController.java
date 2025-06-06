package com.example.taskmanagement.controller;

import com.example.taskmanagement.model.Project;
import com.example.taskmanagement.service.ProjectService;
import com.example.taskmanagement.dto.ProjectRequestDTO;
import com.example.taskmanagement.dto.ProjectResponseDTO;
import com.example.taskmanagement.model.User;
import com.example.taskmanagement.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@Tag(name = "Projects", description = "Project management endpoints")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    @Operation(summary = "Get all projects")
    public List<ProjectResponseDTO> getAllProjects() {
        return projectService.listProjects().stream().map(this::toResponseDTO).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get project by ID")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponseDTO(projectService.getProjectById(id)));
    }

    @GetMapping("/owner/{ownerId}")
    @Operation(summary = "Get projects by owner ID")
    public List<ProjectResponseDTO> getProjectsByOwner(@PathVariable Long ownerId) {
        return projectService.listProjectsByOwner(ownerId).stream().map(this::toResponseDTO).toList();
    }

    @PostMapping
    @Operation(summary = "Create a new project")
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody @Valid ProjectRequestDTO dto) {
        Project project = fromRequestDTO(dto);
        return ResponseEntity.ok(toResponseDTO(projectService.createProject(project)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a project")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Long id, @RequestBody @Valid ProjectRequestDTO dto) {
        Project updated = fromRequestDTO(dto);
        return ResponseEntity.ok(toResponseDTO(projectService.updateProject(id, updated)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a project")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{projectId}/members/{userId}")
    @Operation(summary = "Add a member to a project")
    public ResponseEntity<Project> addMember(@PathVariable Long projectId, @PathVariable Long userId) {
        return ResponseEntity.ok(projectService.addMember(projectId, userId));
    }

    @DeleteMapping("/{projectId}/members/{userId}")
    @Operation(summary = "Remove a member from a project")
    public ResponseEntity<Project> removeMember(@PathVariable Long projectId, @PathVariable Long userId) {
        return ResponseEntity.ok(projectService.removeMember(projectId, userId));
    }

    @PostMapping("/{projectId}/tasks/{taskId}")
    @Operation(summary = "Add a task to a project")
    public ResponseEntity<Project> addTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        return ResponseEntity.ok(projectService.addTask(projectId, taskId));
    }

    @DeleteMapping("/{projectId}/tasks/{taskId}")
    @Operation(summary = "Remove a task from a project")
    public ResponseEntity<Project> removeTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        return ResponseEntity.ok(projectService.removeTask(projectId, taskId));
    }

    // --- DTO Mapping ---
    private ProjectResponseDTO toResponseDTO(Project project) {
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setStatus(project.getStatus() != null ? project.getStatus().name() : null);
        dto.setOwnerId(project.getOwner() != null ? project.getOwner().getId() : null);
        dto.setMemberIds(project.getMembers().stream().map(User::getId).collect(java.util.stream.Collectors.toSet()));
        dto.setTaskIds(project.getTasks().stream().map(Task::getId).collect(java.util.stream.Collectors.toSet()));
        return dto;
    }

    private Project fromRequestDTO(ProjectRequestDTO dto) {
        Project project = new Project();
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setStartDate(dto.getStartDate());
        project.setEndDate(dto.getEndDate());
        if (dto.getStatus() != null) {
            project.setStatus(Project.ProjectStatus.valueOf(dto.getStatus()));
        }
        // Note: owner and members should be set in the service layer as needed
        return project;
    }
} 