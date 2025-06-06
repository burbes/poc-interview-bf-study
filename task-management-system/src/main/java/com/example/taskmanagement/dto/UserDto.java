package com.example.taskmanagement.dto;

import com.example.taskmanagement.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for User entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private User.UserRole role;
    private Boolean enabled;

    /**
     * Convert User entity to UserDto.
     *
     * @param user User entity
     * @return UserDto
     */
    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .enabled(user.getEnabled())
                .build();
    }

    /**
     * Convert UserDto to User entity.
     *
     * @return User entity
     */
    public User toEntity() {
        return User.builder()
                .id(this.id)
                .username(this.username)
                .email(this.email)
                .fullName(this.fullName)
                .role(this.role)
                .enabled(this.enabled)
                .build();
    }
}

