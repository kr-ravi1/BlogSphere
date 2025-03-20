package com.blog.backend.dto;

import com.blog.backend.models.Role;

public class UserDTO {
    private long id;
    private String name;
    private String email;
    private String city;
    private Role role;

    private boolean subscribed;


    public UserDTO(long id, String name, String email, String city, Role role, boolean subscribed) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.city = city;
        this.role = role;
        this.subscribed = subscribed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }
}
