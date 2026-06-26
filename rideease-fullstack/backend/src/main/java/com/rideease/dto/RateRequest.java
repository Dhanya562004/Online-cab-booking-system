package com.rideease.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class RateRequest {
    @NotNull(message = "Stars rating is required")
    @Min(value = 1, message = "Stars rating must be at least 1")
    @Max(value = 5, message = "Stars rating must be at most 5")
    private Integer stars;

    private String review;

    public RateRequest() {}

    public RateRequest(Integer stars, String review) {
        this.stars = stars;
        this.review = review;
    }

    public Integer getStars() { return stars; }
    public void setStars(Integer stars) { this.stars = stars; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }
}
