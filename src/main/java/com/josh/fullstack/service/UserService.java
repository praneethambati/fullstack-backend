package com.josh.fullstack.service;

import com.josh.fullstack.dto.UserDto;
import com.josh.fullstack.model.User;
import com.josh.fullstack.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public List<UserDto> list() {
        return repo.findAll().stream().map(UserDto::from).toList();
    }

    public UserDto create(String email, String password, String role) {
        repo.findByEmail(email).ifPresent(u -> { throw new ResponseStatusException(HttpStatus.CONFLICT, "Email exists"); });
        User u = new User();
        u.setEmail(email);
        u.setPasswordHash(encoder.encode(password));
        u.setRole(role.toUpperCase());
        u.setActive(true);
        return UserDto.from(repo.save(u));
    }

    public UserDto update(Long id, Boolean active, String role) {
        User u = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (active != null) u.setActive(active);
        if (role != null) u.setRole(role.toUpperCase());
        return UserDto.from(repo.save(u));
    }
}