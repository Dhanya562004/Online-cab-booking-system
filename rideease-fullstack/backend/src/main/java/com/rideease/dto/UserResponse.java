package com.rideease.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponse {
    private Long id;
    private String username;
    private String email;

    @JsonProperty("account_type")
    private String accountType;

    private String role;

    public UserResponse() {}

    public UserResponse(Long id, String username, String email, String accountType, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.accountType = accountType;
        this.role = role;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public static UserResponseBuilder builder() {
        return new UserResponseBuilder();
    }

    public static class UserResponseBuilder {
        private Long id;
        private String username;
        private String email;
        private String accountType;
        private String role;

        public UserResponseBuilder id(Long id) { this.id = id; return this; }
        public UserResponseBuilder username(String username) { this.username = username; return this; }
        public UserResponseBuilder email(String email) { this.email = email; return this; }
        public UserResponseBuilder accountType(String accountType) { this.accountType = accountType; return this; }
        public UserResponseBuilder role(String role) { this.role = role; return this; }

        public UserResponse build() {
            return new UserResponse(id, username, email, accountType, role);
        }
    }
}
