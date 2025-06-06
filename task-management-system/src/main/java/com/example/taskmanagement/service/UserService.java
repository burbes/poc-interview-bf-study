package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.CreateUserRequest;
import com.example.taskmanagement.dto.UpdateUserRequest;
import com.example.taskmanagement.dto.UserDto;
import com.example.taskmanagement.dto.UserResponse;
import com.example.taskmanagement.exception.ResourceNotFoundException;
import com.example.taskmanagement.exception.ValidationException;
import com.example.taskmanagement.model.User;
import com.example.taskmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service for User-related operations.
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Create a new user.
     *
     * @param request User creation request
     * @return Created user
     */
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        log.info("Creating new user with username: {}", request.getUsername());
        
        // Validate unique constraints
        Map<String, String> errors = new HashMap<>();
        if (userRepository.existsByUsername(request.getUsername())) {
            errors.put("username", "Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            errors.put("email", "Email already exists");
        }
        
        if (!errors.isEmpty()) {
            log.warn("Validation errors during user creation: {}", errors);
            throw new ValidationException("User validation failed", errors);
        }
        
        // Encode password and create user
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = request.toEntity(encodedPassword);
        
        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getId());
        
        return UserResponse.fromEntity(savedUser);
    }

    /**
     * Get a user by ID.
     *
     * @param id User ID
     * @return User
     */
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        log.info("Getting user with ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        return UserResponse.fromEntity(user);
    }

    /**
     * Get a user by username.
     *
     * @param username Username
     * @return User
     */
    @Transactional(readOnly = true)
    public UserResponse getUserByUsername(String username) {
        log.info("Getting user with username: {}", username);
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        
        return UserResponse.fromEntity(user);
    }

    /**
     * Get all users.
     *
     * @param pageable Pagination information
     * @return Page of users
     */
    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        log.info("Getting all users with pagination: {}", pageable);
        
        return userRepository.findAll(pageable)
                .map(UserResponse::fromEntity);
    }

    /**
     * Update a user.
     *
     * @param id User ID
     * @param request User update request
     * @return Updated user
     */
    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        log.info("Updating user with ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        // Validate email uniqueness if changed
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                log.warn("Email already exists: {}", request.getEmail());
                throw new ValidationException("Email already exists");
            }
        }
        
        User updatedUser = request.updateEntity(user);
        User savedUser = userRepository.save(updatedUser);
        
        log.info("User updated successfully with ID: {}", savedUser.getId());
        return UserResponse.fromEntity(savedUser);
    }

    /**
     * Delete a user.
     *
     * @param id User ID
     */
    @Transactional
    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);
        
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        
        userRepository.deleteById(id);
        log.info("User deleted successfully with ID: {}", id);
    }

    /**
     * Get users by role.
     *
     * @param role User role
     * @return List of users
     */
    @Transactional(readOnly = true)
    public List<UserResponse> getUsersByRole(User.UserRole role) {
        log.info("Getting users with role: {}", role);
        
        return userRepository.findByRole(role).stream()
                .map(UserResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Get users by project ID.
     *
     * @param projectId Project ID
     * @return List of users
     */
    @Transactional(readOnly = true)
    public List<UserResponse> getUsersByProjectId(Long projectId) {
        log.info("Getting users for project with ID: {}", projectId);
        
        return userRepository.findByProjectId(projectId).stream()
                .map(UserResponse::fromEntity)
                .collect(Collectors.toList());
    }
}

