package com.cherrybite.util;

import java.util.UUID;

import com.cherrybite.enums.UserRole;

public class CurrentUser {

    private UUID userId;
    
    private String username;
    
    private UserRole role;

    public CurrentUser(UUID userId, String username, UserRole role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }
}