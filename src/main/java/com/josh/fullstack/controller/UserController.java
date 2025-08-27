package com.josh.fullstack.controller;

import com.josh.fullstack.dto.UserDto;
import com.josh.fullstack.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public record CreateUserRequest(@Email @NotBlank String email, @NotBlank String password, @NotBlank String role) {}
    public record UpdateUserRequest(Boolean active, String role) {}

    @GetMapping
    public List<UserDto> list() { return userService.list(); }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody CreateUserRequest r) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(r.email(), r.password(), r.role()));
    }

    @PatchMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UpdateUserRequest r) {
        return userService.update(id, r.active(), r.role());
    }
}
