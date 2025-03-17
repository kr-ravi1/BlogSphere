package com.blog.backend.models;

public enum Role {

    CREATOR, USER, ADMIN;

    @Override
    public String toString() {
        switch (this) {
            case CREATOR:
                return "CREATOR";
            case USER:
                return "USER";
            case ADMIN:
                return "ADMIN";
            default:
                return super.toString();
        }
    }
}
