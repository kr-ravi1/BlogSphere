package com.blog.backend.controllers;

import com.blog.backend.models.User;
import com.blog.backend.repositories.UserRepo;
import com.blog.backend.requests.LoginRequest;
import com.blog.backend.requests.RegisterRequest;
import com.blog.backend.responses.Response;
import com.blog.backend.services.JWTUtils;
import com.blog.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody RegisterRequest registerRequest) {
        Response resp = new Response();
        boolean isAlreadyRegistered = userService.existsByEmail(registerRequest.getEmail());
        if(isAlreadyRegistered) {
            resp.setMessage("Email already registered");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        }
        try {
            User user = new User();
            user.setEmail(registerRequest.getEmail());
            user.setRole(registerRequest.getRole());
            user.setName(registerRequest.getName());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setCity(registerRequest.getCity());
            User userResult = userRepo.save(user);
            if (userResult.getId()>0) {
                resp.setUser((userResult));
                resp.setMessage("User Saved Successfully");
                return ResponseEntity.status(HttpStatus.CREATED).body(resp);
            }

        }catch (Exception e){
            resp.setError(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest){
        Response response = new Response();
        boolean isPresent = userService.existsByEmail(loginRequest.getEmail());
        if(!isPresent) {
            response.setMessage("Email is not registered");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                            loginRequest.getPassword()));
            var user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setToken(jwt);
            response.setRole(user.getRole());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully Logged In");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
