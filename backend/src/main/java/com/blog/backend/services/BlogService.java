package com.blog.backend.services;

import com.blog.backend.dto.BlogDTO;
import com.blog.backend.models.Blog;
import com.blog.backend.models.User;

import java.util.List;
import java.util.Optional;

public interface BlogService {

    Blog saveBlog(Blog blog);

//   List<Blog> getAllBlogsUser(Long userId);

    List<BlogDTO> getAllBlogsByAdmin(Long userId);

    Optional<Blog> getBlogById(long id);

    List<BlogDTO> getAllBlogs();

    boolean deleteBlogById(long id);
}
