package com.example.taskmanagement.dto;

import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest {
    @Test
    void testTaskRequestDTOToEntity() {
        TaskRequestDTO dto = TaskRequestDTO.builder()
                .title("Task")
                .description("desc")
                .status(Task.TaskStatus.TODO)
                .priority(Task.TaskPriority.HIGH)
                .dueDate(LocalDateTime.now())
                .projectId(1L)
                .assigneeId(2L)
                .build();
        Task task = dto.toEntity();
        assertEquals("Task", task.getTitle());
        assertEquals(Task.TaskStatus.TODO, task.getStatus());
    }

    @Test
    void testTaskResponseDTOFromEntity() {
        Task task = Task.builder().id(1L).title("Task").status(Task.TaskStatus.DONE).priority(Task.TaskPriority.LOW).build();
        TaskResponseDTO dto = TaskResponseDTO.fromEntity(task);
        assertEquals(1L, dto.getId());
        assertEquals("Task", dto.getTitle());
        assertEquals(Task.TaskStatus.DONE, dto.getStatus());
    }

    @Test
    void testUserDtoMapping() {
        User user = User.builder().id(1L).username("user").email("e@e.com").fullName("User").role(User.UserRole.USER).enabled(true).build();
        UserDto dto = UserDto.fromEntity(user);
        assertEquals("user", dto.getUsername());
        User entity = dto.toEntity();
        assertEquals("user", entity.getUsername());
    }

    @Test
    void testUserResponseMapping() {
        User user = User.builder().id(1L).username("user").email("e@e.com").fullName("User").role(User.UserRole.USER).enabled(true).build();
        UserResponse dto = UserResponse.fromEntity(user);
        assertEquals("user", dto.getUsername());
    }

    @Test
    void testProjectRequestDTO() {
        ProjectRequestDTO dto = new ProjectRequestDTO();
        dto.setName("Project");
        dto.setDescription("desc");
        dto.setStartDate(LocalDate.now());
        dto.setEndDate(LocalDate.now().plusDays(1));
        dto.setStatus("ACTIVE");
        dto.setMemberIds(Set.of(1L, 2L));
        assertEquals("Project", dto.getName());
    }

    @Test
    void testProjectResponseDTO() {
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.setId(1L);
        dto.setName("Project");
        dto.setDescription("desc");
        dto.setStartDate(LocalDate.now());
        dto.setEndDate(LocalDate.now().plusDays(1));
        dto.setStatus("ACTIVE");
        dto.setOwnerId(1L);
        dto.setMemberIds(Set.of(1L, 2L));
        dto.setTaskIds(Set.of(10L, 11L));
        assertEquals("Project", dto.getName());
    }

    @Test
    void testCreateUserRequestToEntity() {
        CreateUserRequest req = CreateUserRequest.builder()
                .username("user")
                .email("e@e.com")
                .fullName("User")
                .password("Password1!")
                .build();
        User user = req.toEntity("encoded");
        assertEquals("user", user.getUsername());
        assertEquals("encoded", user.getPassword());
    }

    @Test
    void testUpdateUserRequestUpdateEntity() {
        User user = User.builder().id(1L).username("user").email("e@e.com").fullName("User").role(User.UserRole.USER).enabled(true).build();
        UpdateUserRequest req = UpdateUserRequest.builder().fullName("New Name").email("new@e.com").enabled(false).role(User.UserRole.ADMIN).build();
        User updated = req.updateEntity(user);
        assertEquals("New Name", updated.getFullName());
        assertEquals("new@e.com", updated.getEmail());
        assertFalse(updated.getEnabled());
        assertEquals(User.UserRole.ADMIN, updated.getRole());
    }
} 