package com.example.taskmanagement.controller;

import com.example.taskmanagement.dto.ProjectRequestDTO;
import com.example.taskmanagement.dto.ProjectResponseDTO;
import com.example.taskmanagement.model.Project;
import com.example.taskmanagement.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {
    @Mock
    private ProjectService projectService;
    @InjectMocks
    private ProjectController projectController;

    ProjectResponseDTO projectResponseDTO;
    ProjectRequestDTO projectRequestDTO;
    Project project;

    @BeforeEach
    void setUp() {
        projectResponseDTO = new ProjectResponseDTO();
        projectResponseDTO.setId(1L);
        projectResponseDTO.setName("Test Project");
        projectRequestDTO = new ProjectRequestDTO();
        projectRequestDTO.setName("Test Project");
        project = Project.builder().id(1L).name("Test Project").build();
    }

    @Test
    void getAllProjects_success() {
        when(projectService.listProjects()).thenReturn(List.of(project));
        var result = projectController.getAllProjects();
        assertFalse(result.isEmpty());
    }

    @Test
    void getProjectById_success() {
        when(projectService.getProjectById(1L)).thenReturn(project);
        var result = projectController.getProjectById(1L);
        assertEquals(1L, result.getBody().getId());
    }

    @Test
    void createProject_success() {
        when(projectService.createProject(any(Project.class))).thenReturn(project);
        var result = projectController.createProject(projectRequestDTO);
        assertEquals("Test Project", result.getBody().getName());
    }

    @Test
    void updateProject_success() {
        when(projectService.updateProject(any(Long.class), any(Project.class))).thenReturn(project);
        var result = projectController.updateProject(1L, projectRequestDTO);
        assertEquals("Test Project", result.getBody().getName());
    }

    @Test
    void deleteProject_success() {
        assertDoesNotThrow(() -> projectController.deleteProject(1L));
    }

    @Test
    void getProjectsByOwner_success() {
        when(projectService.listProjectsByOwner(1L)).thenReturn(List.of(project));
        var result = projectController.getProjectsByOwner(1L);
        assertFalse(result.isEmpty());
    }
} 