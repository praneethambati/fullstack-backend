package com.josh.fullstack.config;

import com.josh.fullstack.model.User;
import com.josh.fullstack.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("!test") // skip during tests
public class DataSeeder implements CommandLineRunner {

    private final UserRepository users;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public DataSeeder(UserRepository users) {
        this.users = users;
    }

    @Override
    public void run(String... args) {
        users.findByEmail("admin@example.com").orElseGet(() -> {
            User admin = new User();
            admin.setEmail("admin@example.com");
            admin.setPasswordHash(encoder.encode("Admin@123"));
            admin.setRole("ADMIN");
            admin.setActive(true);
            return users.save(admin);
        });
    }
}