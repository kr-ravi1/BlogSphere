package com.blog.backend.repositories;


import com.blog.backend.dto.UserDTO;
import com.blog.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    public boolean existsByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u")
    List<User> getAllUsers();

    @Query("SELECT u.email FROM User u WHERE u.subscribed = true")
    String[] getAllSubscribers();
}
