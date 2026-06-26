package com.rideease.controller;

import com.rideease.config.JwtAuthenticationFilter.UserPrincipal;
import com.rideease.dto.RateRequest;
import com.rideease.dto.RideHistoryResponse;
import com.rideease.dto.RideRequest;
import com.rideease.model.Rating;
import com.rideease.model.Ride;
import com.rideease.model.User;
import com.rideease.repository.RatingRepository;
import com.rideease.repository.RideRepository;
import com.rideease.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final RideRepository rideRepository;
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;

    public RideController(RideRepository rideRepository, RatingRepository ratingRepository, UserRepository userRepository) {
        this.rideRepository = rideRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> saveRide(@Valid @RequestBody RideRequest request,
                                      BindingResult bindingResult,
                                      @AuthenticationPrincipal UserPrincipal userPrincipal) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing required ride details."));
        }

        Optional<User> userOpt = userRepository.findById(userPrincipal.getId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "User not found."));
        }

        Ride ride = Ride.builder()
                .user(userOpt.get())
                .pickup(request.getPickup())
                .dropLocation(request.getDrop())
                .distance(request.getDistance())
                .durationMin(request.getDurationMin())
                .rideType(request.getRideType())
                .fare(request.getFare())
                .paymentMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : "card")
                .status("completed")
                .build();

        Ride savedRide = rideRepository.save(ride);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("ride", savedRide));
    }

    @GetMapping("/history")
    public ResponseEntity<?> getRideHistory(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<Object[]> rawRides = rideRepository.findRideHistoryByUserId(userPrincipal.getId());
        List<RideHistoryResponse> history = new ArrayList<>();

        for (Object[] row : rawRides) {
            RideHistoryResponse dto = RideHistoryResponse.builder()
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
                    .stars(row[11] != null ? ((Number) row[11]).intValue() : null)
                    .review((String) row[12])
                    .build();
            history.add(dto);
        }

        return ResponseEntity.ok(Map.of("rides", history));
    }

    @PostMapping("/{id}/rate")
    public ResponseEntity<?> rateRide(@PathVariable("id") Long rideId,
                                      @Valid @RequestBody RateRequest request,
                                      BindingResult bindingResult,
                                      @AuthenticationPrincipal UserPrincipal userPrincipal) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Stars must be between 1 and 5."));
        }

        Optional<Ride> rideOpt = rideRepository.findById(rideId);
        if (rideOpt.isEmpty() || !rideOpt.get().getUser().getId().equals(userPrincipal.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Ride not found."));
        }

        if (ratingRepository.existsByRideId(rideId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "You have already rated this ride."));
        }

        Optional<User> userOpt = userRepository.findById(userPrincipal.getId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "User not found."));
        }

        Rating rating = Rating.builder()
                .ride(rideOpt.get())
                .user(userOpt.get())
                .stars(request.getStars())
                .review(request.getReview() != null ? request.getReview() : "")
                .build();

        Rating savedRating = ratingRepository.save(rating);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("rating", savedRating));
    }
}
