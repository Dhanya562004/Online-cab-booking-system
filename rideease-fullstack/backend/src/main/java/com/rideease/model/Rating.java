package com.rideease.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ride_id", nullable = false)
    private Ride ride;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer stars;

    @Column(columnDefinition = "TEXT")
    private String review;

    @Column(name = "created_at", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime createdAt;

    public Rating() {}

    public Rating(Long id, Ride ride, User user, Integer stars, String review, LocalDateTime createdAt) {
        this.id = id;
        this.ride = ride;
        this.user = user;
        this.stars = stars;
        this.review = review;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Ride getRide() { return ride; }
    public void setRide(Ride ride) { this.ride = ride; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Integer getStars() { return stars; }
    public void setStars(Integer stars) { this.stars = stars; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static RatingBuilder builder() {
        return new RatingBuilder();
    }

    public static class RatingBuilder {
        private Long id;
        private Ride ride;
        private User user;
        private Integer stars;
        private String review;
        private LocalDateTime createdAt;

        public RatingBuilder id(Long id) { this.id = id; return this; }
        public RatingBuilder ride(Ride ride) { this.ride = ride; return this; }
        public RatingBuilder user(User user) { this.user = user; return this; }
        public RatingBuilder stars(Integer stars) { this.stars = stars; return this; }
        public RatingBuilder review(String review) { this.review = review; return this; }
        public RatingBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Rating build() {
            return new Rating(id, ride, user, stars, review, createdAt);
        }
    }
}
