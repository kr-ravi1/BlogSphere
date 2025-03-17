package com.blog.backend.services.implementations;

import com.blog.backend.dto.BlogDTO;
import com.blog.backend.models.Blog;
import com.blog.backend.repositories.BlogRepo;
import com.blog.backend.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepo blogRepo;
    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepo.save(blog);
    }

    @Override
    public List<BlogDTO> getAllBlogsByAdmin(Long userId) {
        return blogRepo.getAllBlogsByAdmin(userId).stream()
                .map(blog -> new BlogDTO(blog.getId(), blog.getTitle(), blog.getDate(), truncateContent(blog.getContent(), 20)))
                .collect(Collectors.toList());
    }

    public Optional<Blog> getBlogById(long id) {
        return blogRepo.findBlogById(id);
    }

    public boolean deleteBlogById(long id) {
        try {
            blogRepo.deleteById(id); // Attempt to delete the blog
            return true; // Return true if deletion is successful
        } catch (EmptyResultDataAccessException e) {
            return false; // Return false if the blog does not exist
        }
    }

    private String truncateContent(String content, int wordLimit) {
        if(content == null || content.isEmpty()) {
            return content;
        }
        String[] words = content.split("\\s+");
        return words.length > wordLimit ? String.join(" ", Arrays.copyOfRange(words, 0, wordLimit)) + "..." : content;
    }
    public List<BlogDTO> getAllBlogs() {
        return blogRepo.getAllBlogs().stream()
                .map(blog -> new BlogDTO(blog.getId(), blog.getTitle(), blog.getDate(), truncateContent(blog.getContent(), 20)))
                .collect(Collectors.toList());
    }
}
