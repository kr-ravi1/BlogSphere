package com.blog.backend.controllers;

import com.blog.backend.models.Blog;
import com.blog.backend.responses.Response;
import com.blog.backend.services.BlogSecurity;
import com.blog.backend.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/blog")
@CrossOrigin
@RestController
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogSecurity blogSecurity;

    @GetMapping("/{blogId}")
    public ResponseEntity<Response> getBlogById(@PathVariable long blogId) {
        Response response = new Response();
        boolean isOwner = blogSecurity.isBlogOwner(blogId);
        try{
            Blog blog = blogService.getBlogById(blogId).orElseThrow();
            response.setBlogId(blog.getId());
            response.setTitle(blog.getTitle());
            response.setDate(blog.getDate());
            response.setContent(blog.getContent());
            response.setOwner(isOwner);
            response.setMessage("Blog fetched successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e) {
            response.setMessage("Something went wrong");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
