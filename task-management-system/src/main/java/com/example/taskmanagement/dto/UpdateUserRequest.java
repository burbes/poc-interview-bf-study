package com.example.taskmanagement.dto;

import com.example.taskmanagement.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for updating a user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {

    @Size(max = 100, message = "Full name must be less than 100 characters")
    private String fullName;

    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must be less than 100 characters")
    private String email;

    private Boolean enabled;

    private User.UserRole role;

    /**
     * Update a User entity with the request data.
     *
     * @param user User to update
     * @return Updated User
     */
    public User updateEntity(User user) {
        if (this.fullName != null) {
            user.setFullName(this.fullName);
        }
        if (this.email != null) {
            user.setEmail(this.email);
        }
        if (this.enabled != null) {
            user.setEnabled(this.enabled);
        }
        if (this.role != null) {
            user.setRole(this.role);
        }
        return user;
    }
}

