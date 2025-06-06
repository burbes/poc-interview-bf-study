package com.example.taskmanagement.service;

import com.example.taskmanagement.exception.ResourceNotFoundException;
import com.example.taskmanagement.model.Project;
import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.model.User;
import com.example.taskmanagement.repository.ProjectRepository;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @Mock ProjectRepository projectRepository;
    @Mock UserRepository userRepository;
    @Mock TaskRepository taskRepository;
    @InjectMocks ProjectService projectService;

    Project project;
    User user;
    Task task;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).username("testuser").build();
        project = Project.builder().id(1L).name("Test Project").owner(user).build();
        task = Task.builder().id(1L).title("Test Task").project(project).build();
    }

    @Test
    void createProject_success() {
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        Project saved = projectService.createProject(project);
        assertEquals(project.getName(), saved.getName());
    }

    @Test
    void getProjectById_found() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        Project found = projectService.getProjectById(1L);
        assertEquals(project.getId(), found.getId());
    }

    @Test
    void getProjectById_notFound() {
        when(projectRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> projectService.getProjectById(2L));
    }

    @Test
    void listProjects() {
        when(projectRepository.findAll()).thenReturn(List.of(project));
        List<Project> projects = projectService.listProjects();
        assertFalse(projects.isEmpty());
    }

    @Test
    void listProjectsByOwner() {
        when(projectRepository.findByOwnerId(1L)).thenReturn(List.of(project));
        List<Project> projects = projectService.listProjectsByOwner(1L);
        assertEquals(1, projects.size());
    }

    @Test
    void updateProject_success() {
        Project updated = Project.builder().name("Updated").description("desc").status(Project.ProjectStatus.ACTIVE).build();
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(updated);
        Project result = projectService.updateProject(1L, updated);
        assertEquals("Updated", result.getName());
    }

    @Test
    void deleteProject_success() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        doNothing().when(projectRepository).delete(project);
        assertDoesNotThrow(() -> projectService.deleteProject(1L));
    }

    @Test
    void addMember_success() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        Project result = projectService.addMember(1L, 1L);
        assertNotNull(result);
    }

    @Test
    void removeMember_success() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        Project result = projectService.removeMember(1L, 1L);
        assertNotNull(result);
    }

    @Test
    void addTask_success() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        Project result = projectService.addTask(1L, 1L);
        assertNotNull(result);
    }

    @Test
    void removeTask_success() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        Project result = projectService.removeTask(1L, 1L);
        assertNotNull(result);
    }
} 