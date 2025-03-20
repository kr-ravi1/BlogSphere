package com.blog.backend.services.implementations;

import com.blog.backend.dto.UserDTO;
import com.blog.backend.models.User;
import com.blog.backend.repositories.UserRepo;
import com.blog.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepo.getAllUsers().stream()
                .filter(user -> !"ADMIN".equalsIgnoreCase(user.getRole().toString()))
                .map(user -> new UserDTO(user.getId(),user.getName(), user.getEmail(), user.getCity(), user.getRole(), user.isSubscribed()))
                .collect(Collectors.toList());
    }

    @Override
    public String[] getAllSubscribers() {
        return userRepo.getAllSubscribers();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }

}
