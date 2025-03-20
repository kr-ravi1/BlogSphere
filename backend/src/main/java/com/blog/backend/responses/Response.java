package com.blog.backend.responses;

import com.blog.backend.dto.BlogDTO;
import com.blog.backend.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.blog.backend.models.Role;
import com.blog.backend.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    private Long id;
    private String email;
    private String name;
    private String token;
    private String refreshToken;

    private Role role;
    private String message;
    private UserDTO user;
    private Long blogId;
    private String title;
    private String date;
    private String content;

    private Boolean isOwner;

    private Boolean subscribed;

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

    private List<UserDTO> allUsers;

    public List<UserDTO> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<UserDTO> allUsers) {
        this.allUsers = allUsers;
    }

    public Boolean getOwner() {
        return isOwner;
    }

    public void setOwner(Boolean owner) {
        isOwner = owner;
    }

    public List<BlogDTO> allBlogs;

    public List<BlogDTO> getAllBlogs() {
        return allBlogs;
    }

    public void setAllBlogs(List<BlogDTO> allBlogs) {
        this.allBlogs = allBlogs;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String expirationTime;

    private List<BlogDTO> allBlogsByCreator;

    public List<BlogDTO> getAllBlogsByCreator() {
        return allBlogsByCreator;
    }

    public void setAllBlogsByCreator(List<BlogDTO> allBlogsByCreator) {
        this.allBlogsByCreator = allBlogsByCreator;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
