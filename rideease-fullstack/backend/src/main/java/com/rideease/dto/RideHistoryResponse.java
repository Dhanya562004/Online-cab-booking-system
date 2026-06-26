package com.rideease.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RideHistoryResponse {
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

    private Integer stars;
    private String review;

    public RideHistoryResponse() {}

    public RideHistoryResponse(Long id, Long userId, String pickup, String dropLocation, BigDecimal distance, Integer durationMin, String rideType, Integer fare, String paymentMethod, String status, LocalDateTime createdAt, Integer stars, String review) {
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

    public Integer getStars() { return stars; }
    public void setStars(Integer stars) { this.stars = stars; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

    public static RideHistoryResponseBuilder builder() {
        return new RideHistoryResponseBuilder();
    }

    public static class RideHistoryResponseBuilder {
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
        private Integer stars;
        private String review;

        public RideHistoryResponseBuilder id(Long id) { this.id = id; return this; }
        public RideHistoryResponseBuilder userId(Long userId) { this.userId = userId; return this; }
        public RideHistoryResponseBuilder pickup(String pickup) { this.pickup = pickup; return this; }
        public RideHistoryResponseBuilder dropLocation(String dropLocation) { this.dropLocation = dropLocation; return this; }
        public RideHistoryResponseBuilder distance(BigDecimal distance) { this.distance = distance; return this; }
        public RideHistoryResponseBuilder durationMin(Integer durationMin) { this.durationMin = durationMin; return this; }
        public RideHistoryResponseBuilder rideType(String rideType) { this.rideType = rideType; return this; }
        public RideHistoryResponseBuilder fare(Integer fare) { this.fare = fare; return this; }
        public RideHistoryResponseBuilder paymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; return this; }
        public RideHistoryResponseBuilder status(String status) { this.status = status; return this; }
        public RideHistoryResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public RideHistoryResponseBuilder stars(Integer stars) { this.stars = stars; return this; }
        public RideHistoryResponseBuilder review(String review) { this.review = review; return this; }

        public RideHistoryResponse build() {
            return new RideHistoryResponse(id, userId, pickup, dropLocation, distance, durationMin, rideType, fare, paymentMethod, status, createdAt, stars, review);
        }
    }
}
