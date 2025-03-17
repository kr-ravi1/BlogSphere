package com.blog.backend.services.implementations;

import com.blog.backend.repositories.UserRepo;
import com.blog.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }
}
