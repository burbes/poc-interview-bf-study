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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock TaskRepository taskRepository;
    @Mock ProjectRepository projectRepository;
    @Mock UserRepository userRepository;
    @InjectMocks TaskService taskService;

    Task task;
    Project project;
    User user;

    @BeforeEach
    void setUp() {
        project = Project.builder().id(1L).name("Test Project").build();
        user = User.builder().id(1L).username("testuser").build();
        task = Task.builder().id(1L).title("Test Task").project(project).assignee(user).dueDate(LocalDateTime.now()).build();
    }

    @Test
    void createTask_success() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        Task saved = taskService.createTask(task);
        assertEquals(task.getTitle(), saved.getTitle());
    }

    @Test
    void getTaskById_found() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Task found = taskService.getTaskById(1L);
        assertEquals(task.getId(), found.getId());
    }

    @Test
    void getTaskById_notFound() {
        when(taskRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> taskService.getTaskById(2L));
    }

    @Test
    void listTasks() {
        when(taskRepository.findAll()).thenReturn(List.of(task));
        List<Task> tasks = taskService.listTasks();
        assertFalse(tasks.isEmpty());
    }

    @Test
    void listTasksByProject() {
        when(taskRepository.findByProjectId(1L)).thenReturn(List.of(task));
        List<Task> tasks = taskService.listTasksByProject(1L);
        assertEquals(1, tasks.size());
    }

    @Test
    void listTasksByAssignee() {
        when(taskRepository.findByAssigneeId(1L)).thenReturn(List.of(task));
        List<Task> tasks = taskService.listTasksByAssignee(1L);
        assertEquals(1, tasks.size());
    }

    @Test
    void updateTask_success() {
        Task updated = Task.builder().title("Updated").description("desc").status(Task.TaskStatus.DONE).priority(Task.TaskPriority.HIGH).dueDate(LocalDateTime.now()).build();
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updated);
        Task result = taskService.updateTask(1L, updated);
        assertEquals("Updated", result.getTitle());
    }

    @Test
    void deleteTask_success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).delete(task);
        assertDoesNotThrow(() -> taskService.deleteTask(1L));
    }

    @Test
    void assignTask_success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        Task result = taskService.assignTask(1L, 1L);
        assertNotNull(result);
    }

    @Test
    void updateStatus_success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        Task result = taskService.updateStatus(1L, Task.TaskStatus.DONE);
        assertEquals(Task.TaskStatus.DONE, result.getStatus());
    }

    @Test
    void updatePriority_success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        Task result = taskService.updatePriority(1L, Task.TaskPriority.HIGH);
        assertEquals(Task.TaskPriority.HIGH, result.getPriority());
    }
} 