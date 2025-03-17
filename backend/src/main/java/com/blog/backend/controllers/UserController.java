package com.blog.backend.controllers;

import com.blog.backend.dto.BlogDTO;
import com.blog.backend.responses.Response;
import com.blog.backend.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/creatoruser")
public class UserController {
    @Autowired
    BlogService blogService;

    @GetMapping("")
    public ResponseEntity<Response> getAllBlogs() {
        Response response = new Response();
        try{
            List<BlogDTO> allBlogs = blogService.getAllBlogs();
            response.setAllBlogs(allBlogs);
            response.setMessage("Blogs fetched successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e) {
            response.setMessage("User not Found");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
