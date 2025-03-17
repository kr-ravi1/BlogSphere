package com.blog.backend.services;

import com.blog.backend.models.Blog;
import com.blog.backend.repositories.BlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BlogSecurity {

    @Autowired
    private BlogRepo blogRepo;

    public boolean isBlogOwner(Long blogId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Blog blog = blogRepo.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        return blog.getUser().getUsername().equals(username);
    }
}
