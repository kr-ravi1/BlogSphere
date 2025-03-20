package com.blog.backend.services;

import com.blog.backend.dto.UserDTO;
import com.blog.backend.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public boolean existsByEmail(String email);

    public List<UserDTO> getAllUsers();

    String[] getAllSubscribers();

    Optional<User> getUserByEmail(String email);

    void save(User user);
}
