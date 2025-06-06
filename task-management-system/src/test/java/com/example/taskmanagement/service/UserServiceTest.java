package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.CreateUserRequest;
import com.example.taskmanagement.dto.UpdateUserRequest;
import com.example.taskmanagement.dto.UserResponse;
import com.example.taskmanagement.exception.ResourceNotFoundException;
import com.example.taskmanagement.exception.ValidationException;
import com.example.taskmanagement.model.User;
import com.example.taskmanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_success() {
        CreateUserRequest req = CreateUserRequest.builder()
                .username("user1").email("user1@email.com").password("pass").build();
        when(userRepository.existsByUsername("user1")).thenReturn(false);
        when(userRepository.existsByEmail("user1@email.com")).thenReturn(false);
        when(passwordEncoder.encode("pass")).thenReturn("encoded");
        User user = req.toEntity("encoded");
        User saved = new User(); saved.setId(1L); saved.setUsername("user1");
        when(userRepository.save(any(User.class))).thenReturn(saved);
        UserResponse resp = userService.createUser(req);
        assertEquals("user1", resp.getUsername());
    }

    @Test
    void createUser_duplicateUsernameOrEmail() {
        CreateUserRequest req = CreateUserRequest.builder()
                .username("user1").email("user1@email.com").password("pass").build();
        when(userRepository.existsByUsername("user1")).thenReturn(true);
        when(userRepository.existsByEmail("user1@email.com")).thenReturn(true);
        ValidationException ex = assertThrows(ValidationException.class, () -> userService.createUser(req));
        assertTrue(ex.getErrors().containsKey("username"));
        assertTrue(ex.getErrors().containsKey("email"));
    }

    @Test
    void getUserById_success() {
        User user = new User(); user.setId(1L); user.setUsername("user1");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserResponse resp = userService.getUserById(1L);
        assertEquals("user1", resp.getUsername());
    }

    @Test
    void getUserById_notFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void getUserByUsername_success() {
        User user = new User(); user.setId(1L); user.setUsername("user1");
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        UserResponse resp = userService.getUserByUsername("user1");
        assertEquals("user1", resp.getUsername());
    }

    @Test
    void getUserByUsername_notFound() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserByUsername("user1"));
    }

    @Test
    void getAllUsers_success() {
        User user = new User(); user.setId(1L); user.setUsername("user1");
        Page<User> page = new PageImpl<>(List.of(user));
        when(userRepository.findAll(any(Pageable.class))).thenReturn(page);
        Page<UserResponse> result = userService.getAllUsers(PageRequest.of(0, 10));
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void updateUser_success() {
        User user = new User(); user.setId(1L); user.setEmail("old@email.com");
        UpdateUserRequest req = UpdateUserRequest.builder().email("new@email.com").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail("new@email.com")).thenReturn(false);
        User updated = req.updateEntity(user);
        when(userRepository.save(any(User.class))).thenReturn(updated);
        UserResponse resp = userService.updateUser(1L, req);
        assertEquals("new@email.com", resp.getEmail());
    }

    @Test
    void updateUser_emailExists() {
        User user = new User(); user.setId(1L); user.setEmail("old@email.com");
        UpdateUserRequest req = UpdateUserRequest.builder().email("new@email.com").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail("new@email.com")).thenReturn(true);
        assertThrows(ValidationException.class, () -> userService.updateUser(1L, req));
    }

    @Test
    void updateUser_notFound() {
        UpdateUserRequest req = UpdateUserRequest.builder().email("new@email.com").build();
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(1L, req));
    }

    @Test
    void deleteUser_success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);
        assertDoesNotThrow(() -> userService.deleteUser(1L));
        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_notFound() {
        when(userRepository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));
    }

    @Test
    void getUsersByRole_success() {
        User user = new User(); user.setId(1L); user.setRole(User.UserRole.USER);
        when(userRepository.findByRole(User.UserRole.USER)).thenReturn(List.of(user));
        List<UserResponse> result = userService.getUsersByRole(User.UserRole.USER);
        assertEquals(1, result.size());
    }

    @Test
    void getUsersByProjectId_success() {
        User user = new User(); user.setId(1L);
        when(userRepository.findByProjectId(1L)).thenReturn(List.of(user));
        List<UserResponse> result = userService.getUsersByProjectId(1L);
        assertEquals(1, result.size());
    }
} 