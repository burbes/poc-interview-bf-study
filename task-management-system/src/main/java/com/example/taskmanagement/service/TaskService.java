package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Project;
import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.model.User;
import com.example.taskmanagement.repository.ProjectRepository;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.repository.UserRepository;
import com.example.taskmanagement.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    public List<Task> listTasks() {
        return taskRepository.findAll();
    }

    public List<Task> listTasksByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public List<Task> listTasksByAssignee(Long assigneeId) {
        return taskRepository.findByAssigneeId(assigneeId);
    }

    public Task updateTask(Long id, Task updated) {
        Task task = getTaskById(id);
        task.setTitle(updated.getTitle());
        task.setDescription(updated.getDescription());
        task.setStatus(updated.getStatus());
        task.setPriority(updated.getPriority());
        task.setDueDate(updated.getDueDate());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }

    @Transactional
    public Task assignTask(Long taskId, Long userId) {
        Task task = getTaskById(taskId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        task.assignTo(user);
        return taskRepository.save(task);
    }

    @Transactional
    public Task updateStatus(Long taskId, Task.TaskStatus status) {
        Task task = getTaskById(taskId);
        task.setStatus(status);
        return taskRepository.save(task);
    }

    @Transactional
    public Task updatePriority(Long taskId, Task.TaskPriority priority) {
        Task task = getTaskById(taskId);
        task.setPriority(priority);
        return taskRepository.save(task);
    }
} 