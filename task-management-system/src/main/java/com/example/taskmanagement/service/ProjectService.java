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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
    }

    public List<Project> listProjects() {
        return projectRepository.findAll();
    }

    public List<Project> listProjectsByOwner(Long ownerId) {
        return projectRepository.findByOwnerId(ownerId);
    }

    public Project updateProject(Long id, Project updated) {
        Project project = getProjectById(id);
        project.setName(updated.getName());
        project.setDescription(updated.getDescription());
        project.setStatus(updated.getStatus());
        project.setStartDate(updated.getStartDate());
        project.setEndDate(updated.getEndDate());
        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        Project project = getProjectById(id);
        projectRepository.delete(project);
    }

    @Transactional
    public Project addMember(Long projectId, Long userId) {
        Project project = getProjectById(projectId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        project.addMember(user);
        return projectRepository.save(project);
    }

    @Transactional
    public Project removeMember(Long projectId, Long userId) {
        Project project = getProjectById(projectId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        project.removeMember(user);
        return projectRepository.save(project);
    }

    @Transactional
    public Project addTask(Long projectId, Long taskId) {
        Project project = getProjectById(projectId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        project.addTask(task);
        return projectRepository.save(project);
    }

    @Transactional
    public Project removeTask(Long projectId, Long taskId) {
        Project project = getProjectById(projectId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        project.removeTask(task);
        return projectRepository.save(project);
    }
} 