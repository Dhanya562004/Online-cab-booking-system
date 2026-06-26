package com.rideease.repository;

import com.rideease.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role <> :adminRole")
    long countUsersExcludeRole(@Param("adminRole") String adminRole);

    @Query(value = "SELECT u.id, u.username, u.email, u.account_type AS accountType, u.role, u.created_at AS createdAt, " +
            "COUNT(r.id) AS totalRides " +
            "FROM users u " +
            "LEFT JOIN rides r ON r.user_id = u.id " +
            "GROUP BY u.id " +
            "ORDER BY u.created_at DESC", nativeQuery = true)
    List<Object[]> findUsersWithRideCount();
}
