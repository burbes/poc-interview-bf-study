package com.example.taskmanagement.controller;

import com.example.taskmanagement.dto.TaskRequestDTO;
import com.example.taskmanagement.dto.TaskResponseDTO;
import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.service.TaskService;
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
class TaskControllerTest {
    @Mock
    private TaskService taskService;
    
    @InjectMocks
    private TaskController taskController;

    private TaskResponseDTO taskResponseDTO;
    private TaskRequestDTO taskRequestDTO;
    private Task task;

    @BeforeEach
    void setUp() {
        taskResponseDTO = new TaskResponseDTO();
        taskResponseDTO.setId(1L);
        taskResponseDTO.setTitle("Test Task");
        
        taskRequestDTO = new TaskRequestDTO();
        taskRequestDTO.setTitle("Test Task");
        
        task = Task.builder().id(1L).title("Test Task").build();
    }

    @Test
    void getAllTasks_success() {
        when(taskService.listTasks()).thenReturn(List.of(task));
        var result = taskController.getAllTasks();
        assertFalse(result.isEmpty());
    }

    @Test
    void getTaskById_success() {
        when(taskService.getTaskById(1L)).thenReturn(task);
        var result = taskController.getTaskById(1L);
        assertEquals(1L, result.getBody().getId());
    }

    @Test
    void createTask_success() {
        when(taskService.createTask(any(Task.class))).thenReturn(task);
        var result = taskController.createTask(taskRequestDTO);
        assertEquals("Test Task", result.getBody().getTitle());
    }

    @Test
    void updateTask_success() {
        when(taskService.updateTask(any(Long.class), any(Task.class))).thenReturn(task);
        var result = taskController.updateTask(1L, taskRequestDTO);
        assertEquals("Test Task", result.getBody().getTitle());
    }

    @Test
    void deleteTask_success() {
        assertDoesNotThrow(() -> taskController.deleteTask(1L));
    }

    @Test
    void getTasksByProject_success() {
        when(taskService.listTasksByProject(1L)).thenReturn(List.of(task));
        var result = taskController.getTasksByProject(1L);
        assertFalse(result.isEmpty());
    }

    @Test
    void getTasksByAssignee_success() {
        when(taskService.listTasksByAssignee(1L)).thenReturn(List.of(task));
        var result = taskController.getTasksByAssignee(1L);
        assertFalse(result.isEmpty());
    }
}
