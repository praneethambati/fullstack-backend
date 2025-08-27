package com.josh.fullstack.dto;

import com.josh.fullstack.model.User;

public record UserDto(Long id, String email, String role, boolean active) {
    public static UserDto from(User u) {
        return new UserDto(u.getId(), u.getEmail(), u.getRole(), u.isActive());
    }
}