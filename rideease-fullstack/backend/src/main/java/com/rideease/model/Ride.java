package com.rideease.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rides")
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 255)
    private String pickup;

    @Column(name = "drop_location", nullable = false, length = 255)
    private String dropLocation;

    @Column(precision = 10, scale = 2)
    private BigDecimal distance;

    @Column(name = "duration_min")
    private Integer durationMin;

    @Column(name = "ride_type", length = 50)
    private String rideType;

    private Integer fare;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @Column(length = 50)
    private String status;

    @Column(name = "created_at", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime createdAt;

    public Ride() {}

    public Ride(Long id, User user, String pickup, String dropLocation, BigDecimal distance, Integer durationMin, String rideType, Integer fare, String paymentMethod, String status, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.pickup = pickup;
        this.dropLocation = dropLocation;
        this.distance = distance;
        this.durationMin = durationMin;
        this.rideType = rideType;
        this.fare = fare;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

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

    public static RideBuilder builder() {
        return new RideBuilder();
    }

    public static class RideBuilder {
        private Long id;
        private User user;
        private String pickup;
        private String dropLocation;
        private BigDecimal distance;
        private Integer durationMin;
        private String rideType;
        private Integer fare;
        private String paymentMethod;
        private String status;
        private LocalDateTime createdAt;

        public RideBuilder id(Long id) { this.id = id; return this; }
        public RideBuilder user(User user) { this.user = user; return this; }
        public RideBuilder pickup(String pickup) { this.pickup = pickup; return this; }
        public RideBuilder dropLocation(String dropLocation) { this.dropLocation = dropLocation; return this; }
        public RideBuilder distance(BigDecimal distance) { this.distance = distance; return this; }
        public RideBuilder durationMin(Integer durationMin) { this.durationMin = durationMin; return this; }
        public RideBuilder rideType(String rideType) { this.rideType = rideType; return this; }
        public RideBuilder fare(Integer fare) { this.fare = fare; return this; }
        public RideBuilder paymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; return this; }
        public RideBuilder status(String status) { this.status = status; return this; }
        public RideBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Ride build() {
            return new Ride(id, user, pickup, dropLocation, distance, durationMin, rideType, fare, paymentMethod, status, createdAt);
        }
    }
}
