package com.rideease.controller;

import com.rideease.config.JwtTokenProvider;
import com.rideease.dto.AuthResponse;
import com.rideease.dto.LoginRequest;
import com.rideease.dto.RegisterRequest;
import com.rideease.dto.UserResponse;
import com.rideease.model.User;
import com.rideease.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("error", "All fields are required."));
        }

        String password = request.getPassword();
        if (password.length() < 6) {
            return ResponseEntity.badRequest().body(Map.of("error", "Password must be at least 6 characters."));
        }
        if (!password.matches(".*[A-Z].*")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Password must contain at least 1 uppercase letter."));
        }
        if (!password.matches(".*\\d.*")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Password must contain at least 1 digit."));
        }

        if (userRepository.existsByUsername(request.getUsername()) || userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Username or email already taken."));
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(password))
                .accountType(request.getAccountType() != null ? request.getAccountType() : "rider")
                .role("user") // Default role is user
                .build();

        User savedUser = userRepository.save(user);

        String token = tokenProvider.generateToken(savedUser.getId(), savedUser.getUsername(), savedUser.getRole());

        UserResponse userResponse = UserResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .accountType(savedUser.getAccountType())
                .role(savedUser.getRole())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(token, userResponse));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username and password are required."));
        }

        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid username or password."));
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid username or password."));
        }

        String token = tokenProvider.generateToken(user.getId(), user.getUsername(), user.getRole());

        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .accountType(user.getAccountType())
                .role(user.getRole())
                .build();

        return ResponseEntity.ok(new AuthResponse(token, userResponse));
    }
}
