package com.example.taskmanagement.controller;

import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.taskmanagement.dto.TaskRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * MVC tests for the TaskController REST endpoints.
 */
@WebMvcTest(
    controllers = TaskController.class,
    excludeAutoConfiguration = {JpaRepositoriesAutoConfiguration.class, HibernateJpaAutoConfiguration.class}
)
@ActiveProfiles("test")
@Import(TaskControllerMvcTest.NoJpaAuditingConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @MockBean
    private org.springframework.data.domain.AuditorAware<String> auditorAware;

    @Test
    public void testGetAllTasks() throws Exception {
        // Prepare test data
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task 1");
        task1.setDescription("Description for task 1");
        task1.setStatus(Task.TaskStatus.TODO);
        task1.setPriority(Task.TaskPriority.MEDIUM);
        
        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");
        task2.setDescription("Description for task 2");
        task2.setStatus(Task.TaskStatus.IN_PROGRESS);
        task2.setPriority(Task.TaskPriority.HIGH);
        
        tasks.add(task1);
        tasks.add(task2);

        // Mock service response
        when(taskService.listTasks()).thenReturn(tasks);

        // Perform the request and verify the response
        mockMvc.perform(get("/tasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Task 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Task 2")));
    }

    @Test
    public void testCreateTask() throws Exception {
        // Prepare test data
        TaskRequestDTO newTaskDto = TaskRequestDTO.builder()
                .title("New Task")
                .description("Description for new task")
                .status(Task.TaskStatus.TODO)
                .priority(Task.TaskPriority.HIGH)
                .dueDate(LocalDateTime.now().plusDays(7))
                .projectId(1L)
                .assigneeId(2L)
                .build();

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle("New Task");
        savedTask.setDescription("Description for new task");
        savedTask.setStatus(Task.TaskStatus.TODO);
        savedTask.setPriority(Task.TaskPriority.HIGH);
        savedTask.setDueDate(newTaskDto.getDueDate());

        // Mock service response
        when(taskService.createTask(any(Task.class))).thenReturn(savedTask);

        // Perform the request and verify the response
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTaskDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("New Task")))
                .andExpect(jsonPath("$.status", is("TODO")))
                .andExpect(jsonPath("$.priority", is("HIGH")));
    }

    @Test
    public void testGetTaskById() throws Exception {
        // Prepare test data
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Description for test task");
        task.setStatus(Task.TaskStatus.IN_PROGRESS);
        task.setPriority(Task.TaskPriority.MEDIUM);

        // Mock service response
        when(taskService.getTaskById(1L)).thenReturn(task);

        // Perform the request and verify the response
        mockMvc.perform(get("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Task")))
                .andExpect(jsonPath("$.status", is("IN_PROGRESS")));
    }

    @TestConfiguration
    static class NoJpaAuditingConfig {
        // No beans here: this disables the import of JpaAuditingConfig
    }
}

