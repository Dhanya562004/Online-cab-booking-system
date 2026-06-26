package com.rideease.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class AdminUserResponse {
    private Long id;
    private String username;
    private String email;

    @JsonProperty("account_type")
    private String accountType;

    private String role;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("total_rides")
    private Long totalRides;

    public AdminUserResponse() {}

    public AdminUserResponse(Long id, String username, String email, String accountType, String role, LocalDateTime createdAt, Long totalRides) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.accountType = accountType;
        this.role = role;
        this.createdAt = createdAt;
        this.totalRides = totalRides;
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

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Long getTotalRides() { return totalRides; }
    public void setTotalRides(Long totalRides) { this.totalRides = totalRides; }

    public static AdminUserResponseBuilder builder() {
        return new AdminUserResponseBuilder();
    }

    public static class AdminUserResponseBuilder {
        private Long id;
        private String username;
        private String email;
        private String accountType;
        private String role;
        private LocalDateTime createdAt;
        private Long totalRides;

        public AdminUserResponseBuilder id(Long id) { this.id = id; return this; }
        public AdminUserResponseBuilder username(String username) { this.username = username; return this; }
        public AdminUserResponseBuilder email(String email) { this.email = email; return this; }
        public AdminUserResponseBuilder accountType(String accountType) { this.accountType = accountType; return this; }
        public AdminUserResponseBuilder role(String role) { this.role = role; return this; }
        public AdminUserResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public AdminUserResponseBuilder totalRides(Long totalRides) { this.totalRides = totalRides; return this; }

        public AdminUserResponse build() {
            return new AdminUserResponse(id, username, email, accountType, role, createdAt, totalRides);
        }
    }
}
