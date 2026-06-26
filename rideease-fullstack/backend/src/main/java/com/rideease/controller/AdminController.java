package com.rideease.controller;

import com.rideease.dto.AdminStatsResponse;
import com.rideease.dto.AdminUserResponse;
import com.rideease.dto.AdminRideResponse;
import com.rideease.repository.RatingRepository;
import com.rideease.repository.RideRepository;
import com.rideease.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final RideRepository rideRepository;
    private final RatingRepository ratingRepository;

    public AdminController(UserRepository userRepository, RideRepository rideRepository, RatingRepository ratingRepository) {
        this.userRepository = userRepository;
        this.rideRepository = rideRepository;
        this.ratingRepository = ratingRepository;
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        long totalUsers = userRepository.countUsersExcludeRole("admin");
        long totalRides = rideRepository.count();
        long totalRevenue = rideRepository.sumAllFares();
        Double avg = ratingRepository.getAverageStars();
        String avgRating = avg != null ? String.valueOf(avg) : "0.0";

        AdminStatsResponse stats = AdminStatsResponse.builder()
                .totalUsers(totalUsers)
                .totalRides(totalRides)
                .totalRevenue(totalRevenue)
                .avgRating(avgRating)
                .build();

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        List<Object[]> rawUsers = userRepository.findUsersWithRideCount();
        List<AdminUserResponse> usersList = new ArrayList<>();

        for (Object[] row : rawUsers) {
            AdminUserResponse dto = AdminUserResponse.builder()
                    .id(((Number) row[0]).longValue())
                    .username((String) row[1])
                    .email((String) row[2])
                    .accountType((String) row[3])
                    .role((String) row[4])
                    .createdAt(row[5] != null ? ((Timestamp) row[5]).toLocalDateTime() : null)
                    .totalRides(((Number) row[6]).longValue())
                    .build();
            usersList.add(dto);
        }

        return ResponseEntity.ok(Map.of("users", usersList));
    }

    @GetMapping("/rides")
    public ResponseEntity<?> getRides() {
        List<Object[]> rawRides = rideRepository.findAdminRidesList();
        List<AdminRideResponse> ridesList = new ArrayList<>();

        for (Object[] row : rawRides) {
            AdminRideResponse dto = AdminRideResponse.builder()
                    .id(((Number) row[0]).longValue())
                    .userId(((Number) row[1]).longValue())
                    .pickup((String) row[2])
                    .dropLocation((String) row[3])
                    .distance(row[4] != null ? (BigDecimal) row[4] : null)
                    .durationMin(row[5] != null ? ((Number) row[5]).intValue() : null)
                    .rideType((String) row[6])
                    .fare(row[7] != null ? ((Number) row[7]).intValue() : null)
                    .paymentMethod((String) row[8])
                    .status((String) row[9])
                    .createdAt(row[10] != null ? ((Timestamp) row[10]).toLocalDateTime() : null)
                    .username((String) row[11])
                    .stars(row[12] != null ? ((Number) row[12]).intValue() : null)
                    .review((String) row[13])
                    .build();
            ridesList.add(dto);
        }

        return ResponseEntity.ok(Map.of("rides", ridesList));
    }
}
