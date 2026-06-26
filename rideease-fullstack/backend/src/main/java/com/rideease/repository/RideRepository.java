package com.rideease.repository;

import com.rideease.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {
    
    @Query(value = "SELECT r.id, r.user_id, r.pickup, r.drop_location, r.distance, r.duration_min, " +
            "r.ride_type, r.fare, r.payment_method, r.status, r.created_at, rt.stars, rt.review " +
            "FROM rides r " +
            "LEFT JOIN ratings rt ON rt.ride_id = r.id " +
            "WHERE r.user_id = :userId " +
            "ORDER BY r.created_at DESC", nativeQuery = true)
    List<Object[]> findRideHistoryByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT r.id, r.user_id, r.pickup, r.drop_location, r.distance, r.duration_min, " +
            "r.ride_type, r.fare, r.payment_method, r.status, r.created_at, u.username, rt.stars, rt.review " +
            "FROM rides r " +
            "JOIN users u ON u.id = r.user_id " +
            "LEFT JOIN ratings rt ON rt.ride_id = r.id " +
            "ORDER BY r.created_at DESC " +
            "LIMIT 100", nativeQuery = true)
    List<Object[]> findAdminRidesList();

    @Query("SELECT COALESCE(SUM(r.fare), 0) FROM Ride r")
    long sumAllFares();
}
