package com.blog.backend.repositories;

import com.blog.backend.models.Blog;
import com.blog.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BlogRepo extends JpaRepository<Blog, Long> {

   @Query("SELECT b FROM Blog b WHERE b.user.id = :userId")
   List<Blog> getAllBlogsByAdmin(@Param("userId") Long userId);

   Optional<Blog> findBlogById(long id);

   void deleteById(long id);

   @Query("SELECT b FROM Blog b")
   List<Blog> getAllBlogs();
}
