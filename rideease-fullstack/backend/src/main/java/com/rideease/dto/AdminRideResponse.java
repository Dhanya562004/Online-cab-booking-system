package com.rideease.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AdminRideResponse {
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    private String pickup;

    @JsonProperty("drop_location")
    private String dropLocation;

    private BigDecimal distance;

    @JsonProperty("duration_min")
    private Integer durationMin;

    @JsonProperty("ride_type")
    private String rideType;

    private Integer fare;

    @JsonProperty("payment_method")
    private String paymentMethod;

    private String status;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    private String username;
    private Integer stars;
    private String review;

    public AdminRideResponse() {}

    public AdminRideResponse(Long id, Long userId, String pickup, String dropLocation, BigDecimal distance, Integer durationMin, String rideType, Integer fare, String paymentMethod, String status, LocalDateTime createdAt, String username, Integer stars, String review) {
        this.id = id;
        this.userId = userId;
        this.pickup = pickup;
        this.dropLocation = dropLocation;
        this.distance = distance;
        this.durationMin = durationMin;
        this.rideType = rideType;
        this.fare = fare;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.createdAt = createdAt;
        this.username = username;
        this.stars = stars;
        this.review = review;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getPickup() { return pickup; }
    public void setPickup(String pickup) { this.pickup = pickup; }

    public String getDropLocation() { return dropLocation; }
    public void setDropLocation(String dropLocation) { this.dropLocation = dropLocation; }

    public BigDecimal getDistance() { return distance; }
    public void setDistance(BigDecimal distance) { this.distance = distance; }

    public Integer getDurationMin() { return durationMin; }
    public void setDurationMin(Integer durationMin) { this.durationMin = durationMin; }

    public String getRideType() { return rideType; }
    public void setRideType(String rideType) { this.rideType = rideType; }

    public Integer getFare() { return fare; }
    public void setFare(Integer fare) { this.fare = fare; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Integer getStars() { return stars; }
    public void setStars(Integer stars) { this.stars = stars; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

    public static AdminRideResponseBuilder builder() {
        return new AdminRideResponseBuilder();
    }

    public static class AdminRideResponseBuilder {
        private Long id;
        private Long userId;
        private String pickup;
        private String dropLocation;
        private BigDecimal distance;
        private Integer durationMin;
        private String rideType;
        private Integer fare;
        private String paymentMethod;
        private String status;
        private LocalDateTime createdAt;
        private String username;
        private Integer stars;
        private String review;

        public AdminRideResponseBuilder id(Long id) { this.id = id; return this; }
        public AdminRideResponseBuilder userId(Long userId) { this.userId = userId; return this; }
        public AdminRideResponseBuilder pickup(String pickup) { this.pickup = pickup; return this; }
        public AdminRideResponseBuilder dropLocation(String dropLocation) { this.dropLocation = dropLocation; return this; }
        public AdminRideResponseBuilder distance(BigDecimal distance) { this.distance = distance; return this; }
        public AdminRideResponseBuilder durationMin(Integer durationMin) { this.durationMin = durationMin; return this; }
        public AdminRideResponseBuilder rideType(String rideType) { this.rideType = rideType; return this; }
        public AdminRideResponseBuilder fare(Integer fare) { this.fare = fare; return this; }
        public AdminRideResponseBuilder paymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; return this; }
        public AdminRideResponseBuilder status(String status) { this.status = status; return this; }
        public AdminRideResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public AdminRideResponseBuilder username(String username) { this.username = username; return this; }
        public AdminRideResponseBuilder stars(Integer stars) { this.stars = stars; return this; }
        public AdminRideResponseBuilder review(String review) { this.review = review; return this; }

        public AdminRideResponse build() {
            return new AdminRideResponse(id, userId, pickup, dropLocation, distance, durationMin, rideType, fare, paymentMethod, status, createdAt, username, stars, review);
        }
    }
}
