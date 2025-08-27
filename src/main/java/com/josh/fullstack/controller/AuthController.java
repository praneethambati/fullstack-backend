package com.josh.fullstack.controller;

import com.josh.fullstack.service.AuditService;
import com.josh.fullstack.model.User;
import com.josh.fullstack.repository.UserRepository;
import com.josh.fullstack.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository users;
    private final PasswordEncoder encoder;

    private final AuditService auditService;
    private final JwtUtil jwt;

    public AuthController(UserRepository users, PasswordEncoder encoder, AuditService auditService, JwtUtil jwt) {
        this.users = users;
        this.encoder = encoder;
        this.auditService = auditService;
        this.jwt = jwt;
    }

    public record LoginRequest(@Email @NotBlank String email, @NotBlank String password) {}
    public record LoginResponse(String token) {}

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req, HttpServletRequest request) {
        User u = users.findByEmail(req.email())
                .orElseThrow(() -> {return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");});
        if (!encoder.matches(req.password(), u.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        String token = jwt.generateToken(u.getEmail(), u.getRole());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
