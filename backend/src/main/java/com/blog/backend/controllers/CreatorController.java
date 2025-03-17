package com.blog.backend.controllers;

import com.blog.backend.dto.BlogDTO;
import com.blog.backend.models.Blog;
import com.blog.backend.models.User;
import com.blog.backend.repositories.BlogRepo;
import com.blog.backend.repositories.UserRepo;
import com.blog.backend.requests.BlogRequest;
import com.blog.backend.responses.Response;
import com.blog.backend.services.AuthenticationService;
import com.blog.backend.services.BlogSecurity;
import com.blog.backend.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/creator")
@CrossOrigin
public class CreatorController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogRepo blogRepo;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private BlogSecurity blogSecurity;

    @GetMapping("/test")
    public ResponseEntity<Response> testing() {
        String username = authenticationService.getCurrentUsername();
        Response response = new Response();
        response.setMessage("This is for testing purpose." + username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/blog/add")
    public ResponseEntity<Response> addArticle(@RequestBody BlogRequest blogRequest) {
        Response response = new Response();
        String username = authenticationService.getCurrentUsername();
        try {
            Blog blog = new Blog();
            User user = userRepo.findByEmail(username).orElseThrow();
            blog.setTitle(blogRequest.getTitle());
            blog.setDate(blogRequest.getDate());
            blog.setContent(blogRequest.getContent());
            blog.setUser(user);
            Blog blog1 = blogService.saveBlog(blog);
            if(blog1 != null) {
                response.setMessage("Article Saved Successfully");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
            response.setMessage("Something went wrong.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch(Exception e) {
            response.setMessage("User not Found: " + e);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @GetMapping("")
    public ResponseEntity<Response> getAllBlogs() {
        Response response = new Response();
        try{
            String username = authenticationService.getCurrentUsername();
            User user = userRepo.findByEmail(username).orElseThrow();
            List<BlogDTO> allBlogs = blogService.getAllBlogsByAdmin(user.getId());
            response.setAllBlogsByAdmin(allBlogs);
            response.setMessage("Blogs fetched successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e) {
            response.setMessage("User not Found: "+ e);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/blog/edit/{blogId}")
    public ResponseEntity<Response> edit(@PathVariable long blogId, @RequestBody BlogRequest updatedBlog) {
        Response response = new Response();
        boolean isOwner = blogSecurity.isBlogOwner(blogId);
        response.setOwner(isOwner);
        if(!isOwner) {
            response.setMessage("Sorry you are not the owner of the requested blog.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        try{
            Blog blog = blogService.getBlogById(blogId).orElseThrow();
            blog.setTitle(updatedBlog.getTitle());
            blog.setDate(updatedBlog.getDate());
            blog.setContent(updatedBlog.getContent());
            Blog savedBlog = blogService.saveBlog(blog);
            if(savedBlog != null) {
                response.setMessage("Article Updated Successfully");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
            response.setMessage("Something went wrong.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        catch (Exception e) {
            response.setMessage("Blog not found");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @DeleteMapping("/blog/delete/{blogId}")
    public ResponseEntity<Response> delete(@PathVariable long blogId) {
        Response response = new Response();
        boolean isOwner = blogSecurity.isBlogOwner(blogId);
        if(!isOwner) {
            response.setMessage("Sorry you are not the owner of the requested blog.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        boolean isDeleted = blogService.deleteBlogById(blogId);
        if(isDeleted) {
            response.setMessage("Blog deleted Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        response.setMessage("Blog not Deleted");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
