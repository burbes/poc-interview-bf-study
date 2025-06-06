package com.example.taskmanagement.controller;

import com.example.taskmanagement.dto.CreateUserRequest;
import com.example.taskmanagement.dto.UpdateUserRequest;
import com.example.taskmanagement.dto.UserResponse;
import com.example.taskmanagement.model.User;
import com.example.taskmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for User-related operations.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Log4j2
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    private final UserService userService;

    /**
     * Create a new user.
     *
     * @param request User creation request
     * @return Created user
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "403", description = "Not authorized to create users")
    })
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request) {
        log.info("REST request to create user: {}", request.getUsername());
        UserResponse createdUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * Get a user by ID.
     *
     * @param id User ID
     * @return User
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userSecurity.isCurrentUser(#id)")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to view this user")
    })
    public ResponseEntity<UserResponse> getUserById(
            @Parameter(description = "ID of the user to retrieve") @PathVariable Long id) {
        log.info("REST request to get user with ID: {}", id);
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Get a user by username.
     *
     * @param username Username
     * @return User
     */
    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('ADMIN') or @userSecurity.isCurrentUsername(#username)")
    @Operation(summary = "Get user by username", description = "Retrieves a user by their username")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to view this user")
    })
    public ResponseEntity<UserResponse> getUserByUsername(
            @Parameter(description = "Username of the user to retrieve") @PathVariable String username) {
        log.info("REST request to get user with username: {}", username);
        UserResponse user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    /**
     * Get all users with pagination.
     *
     * @param page Page number
     * @param size Page size
     * @param sort Sort field
     * @param direction Sort direction
     * @return Page of users
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users", description = "Retrieves all users with pagination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
        @ApiResponse(responseCode = "403", description = "Not authorized to view users")
    })
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "id") String sort,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") String direction) {
        log.info("REST request to get all users with pagination - page: {}, size: {}", page, size);
        
        Sort.Direction sortDirection = Sort.Direction.fromString(direction.toUpperCase());
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        
        Page<UserResponse> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    /**
     * Update a user.
     *
     * @param id User ID
     * @param request User update request
     * @return Updated user
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userSecurity.isCurrentUser(#id)")
    @Operation(summary = "Update a user", description = "Updates a user with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "403", description = "Not authorized to update this user")
    })
    public ResponseEntity<UserResponse> updateUser(
            @Parameter(description = "ID of the user to update") @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        log.info("REST request to update user with ID: {}", id);
        UserResponse updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete a user.
     *
     * @param id User ID
     * @return No content
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a user", description = "Deletes a user by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to delete users")
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user to delete") @PathVariable Long id) {
        log.info("REST request to delete user with ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get users by role.
     *
     * @param role User role
     * @return List of users
     */
    @GetMapping("/role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get users by role", description = "Retrieves users with the specified role")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
        @ApiResponse(responseCode = "403", description = "Not authorized to view users by role")
    })
    public ResponseEntity<List<UserResponse>> getUsersByRole(
            @Parameter(description = "Role to filter users by", 
                      schema = @Schema(type = "string", allowableValues = {"ADMIN", "USER", "MANAGER"}))
            @PathVariable String role) {
        log.info("REST request to get users with role: {}", role);
        User.UserRole userRole = User.UserRole.valueOf(role.toUpperCase());
        List<UserResponse> users = userService.getUsersByRole(userRole);
        return ResponseEntity.ok(users);
    }

    /**
     * Get users by project ID.
     *
     * @param projectId Project ID
     * @return List of users
     */
    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasRole('ADMIN') or @projectSecurity.canViewProject(#projectId)")
    @Operation(summary = "Get users by project", description = "Retrieves users associated with the specified project")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
        @ApiResponse(responseCode = "403", description = "Not authorized to view project users")
    })
    public ResponseEntity<List<UserResponse>> getUsersByProjectId(
            @Parameter(description = "ID of the project to get users for") @PathVariable Long projectId) {
        log.info("REST request to get users for project with ID: {}", projectId);
        List<UserResponse> users = userService.getUsersByProjectId(projectId);
        return ResponseEntity.ok(users);
    }
}

