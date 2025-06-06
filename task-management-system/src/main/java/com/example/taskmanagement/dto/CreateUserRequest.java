package com.example.taskmanagement.dto;

import com.example.taskmanagement.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating a new user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Username can only contain letters, numbers, dots, underscores, and hyphens")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must be less than 100 characters")
    private String email;

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name must be less than 100 characters")
    private String fullName;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
        message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and no whitespace"
    )
    private String password;

    /**
     * Convert request to User entity.
     *
     * @param encodedPassword Encoded password
     * @return User entity
     */
    public User toEntity(String encodedPassword) {
        return User.builder()
                .username(this.username)
                .email(this.email)
                .fullName(this.fullName)
                .password(encodedPassword)
                .role(User.UserRole.USER) // Default role
                .enabled(true) // Default enabled
                .build();
    }
}

