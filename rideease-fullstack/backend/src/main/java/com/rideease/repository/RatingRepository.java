package com.rideease.repository;

import com.rideease.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    boolean existsByRideId(Long rideId);

    @Query(value = "SELECT ROUND(AVG(stars)::numeric, 1) FROM ratings", nativeQuery = true)
    Double getAverageStars();
}
