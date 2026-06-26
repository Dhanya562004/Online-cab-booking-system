package com.rideease.dto;

public class AdminStatsResponse {
    private Long totalUsers;
    private Long totalRides;
    private Long totalRevenue;
    private String avgRating;

    public AdminStatsResponse() {}

    public AdminStatsResponse(Long totalUsers, Long totalRides, Long totalRevenue, String avgRating) {
        this.totalUsers = totalUsers;
        this.totalRides = totalRides;
        this.totalRevenue = totalRevenue;
        this.avgRating = avgRating;
    }

    public Long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(Long totalUsers) { this.totalUsers = totalUsers; }

    public Long getTotalRides() { return totalRides; }
    public void setTotalRides(Long totalRides) { this.totalRides = totalRides; }

    public Long getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(Long totalRevenue) { this.totalRevenue = totalRevenue; }

    public String getAvgRating() { return avgRating; }
    public void setAvgRating(String avgRating) { this.avgRating = avgRating; }

    public static AdminStatsResponseBuilder builder() {
        return new AdminStatsResponseBuilder();
    }

    public static class AdminStatsResponseBuilder {
        private Long totalUsers;
        private Long totalRides;
        private Long totalRevenue;
        private String avgRating;

        public AdminStatsResponseBuilder totalUsers(Long totalUsers) { this.totalUsers = totalUsers; return this; }
        public AdminStatsResponseBuilder totalRides(Long totalRides) { this.totalRides = totalRides; return this; }
        public AdminStatsResponseBuilder totalRevenue(Long totalRevenue) { this.totalRevenue = totalRevenue; return this; }
        public AdminStatsResponseBuilder avgRating(String avgRating) { this.avgRating = avgRating; return this; }

        public AdminStatsResponse build() {
            return new AdminStatsResponse(totalUsers, totalRides, totalRevenue, avgRating);
        }
    }
}
