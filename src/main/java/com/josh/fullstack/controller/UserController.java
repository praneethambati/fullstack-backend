package com.josh.fullstack.controller;

import com.josh.fullstack.audit.AuditService;
import com.josh.fullstack.dto.UserDto;
import com.josh.fullstack.model.User;
import com.josh.fullstack.repository.UserRepository;
import com.josh.fullstack.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository users;

    private final AuditService auditService;

    public UserController(UserService userService, UserRepository users, AuditService auditService) {
        this.userService = userService;
        this.users = users;
        this.auditService = auditService;
    }

    public record CreateUserRequest(@Email @NotBlank String email, @NotBlank String password, @NotBlank String role) {}
    public record UpdateUserRequest(Boolean active, String role) {}

    @GetMapping
    public List<UserDto> list() { return userService.list(); }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody CreateUserRequest r, HttpServletRequest request) {
        auditService.log(null, "USER_CREATE", r.email(),
                "{\"email\":\""+r.email()+"\",\"role\":\""+r.role()+"\"}", request);

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(r.email(), r.password(), r.role()));
    }

    @PatchMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UpdateUserRequest r, HttpServletRequest request) {
        auditService.log(null, "USER_UPDATE", null,
                "{\"active\":"+r.active()+",\"role\":\""+r.role()+"\"}", request);

        return userService.update(id, r.active(), r.role());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        User user = users.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        users.delete(user);

        auditService.log(null, "USER_DELETE", user.getEmail(), "{}", request);

        return ResponseEntity.noContent().build();
    }
}
