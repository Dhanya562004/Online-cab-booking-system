package com.rideease.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class RideRequest {
    @NotBlank(message = "Pickup location is required")
    private String pickup;

    @NotBlank(message = "Drop location is required")
    private String drop;

    private BigDecimal distance;

    private Integer durationMin;

    private String rideType;

    @NotNull(message = "Fare is required")
    private Integer fare;

    private String paymentMethod;

    public RideRequest() {}

    public RideRequest(String pickup, String drop, BigDecimal distance, Integer durationMin, String rideType, Integer fare, String paymentMethod) {
        this.pickup = pickup;
        this.drop = drop;
        this.distance = distance;
        this.durationMin = durationMin;
        this.rideType = rideType;
        this.fare = fare;
        this.paymentMethod = paymentMethod;
    }

    public String getPickup() { return pickup; }
    public void setPickup(String pickup) { this.pickup = pickup; }

    public String getDrop() { return drop; }
    public void setDrop(String drop) { this.drop = drop; }

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
}
