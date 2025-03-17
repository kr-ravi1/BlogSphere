package com.blog.backend.repositories;


import com.blog.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    public boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
