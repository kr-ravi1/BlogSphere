package com.blog.backend.config;

import com.blog.backend.models.User;
import com.blog.backend.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.blog.backend.models.Role.ADMIN;

@Component
public class AdminInitializer implements ApplicationRunner {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        userRepo.findByEmail(adminEmail).ifPresentOrElse(admin -> System.out.println("Admin user already exists"),
                () -> {
                    User admin = new User();
                    admin.setEmail(adminEmail);
                    admin.setName("Admin");
                    admin.setPassword(passwordEncoder.encode(adminPassword));
                    admin.setRole(ADMIN);
                    userRepo.save(admin);
                });
    }
}

