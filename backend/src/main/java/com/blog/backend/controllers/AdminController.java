package com.blog.backend.controllers;

import com.blog.backend.dto.UserDTO;
import com.blog.backend.models.User;
import com.blog.backend.repositories.UserRepo;
import com.blog.backend.responses.Response;
import com.blog.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public ResponseEntity<Response> test() {
        Response response = new Response();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<Response> getAllUsers() {
        Response response = new Response();
        try{
            List<UserDTO> userList = userService.getAllUsers();
            response.setAllUsers(userList);
        }
        catch (Exception e) {
            response.setMessage("Unable to fetch all users.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
