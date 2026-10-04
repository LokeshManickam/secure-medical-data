package com.medicalsecurity.service;

import com.medicalsecurity.dto.LoginRequest;
import com.medicalsecurity.dto.LoginResponse;
import com.medicalsecurity.entity.AccountStatus;
import com.medicalsecurity.entity.User;
import com.medicalsecurity.repository.UserRepository;
import com.medicalsecurity.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Value("${security.login.max-failed-attempts}")
    private int maxFailedAttempts;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByUsername(request.username())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid username or password"
                        ));

        // Only ACTIVE accounts are allowed to authenticate.
        if (user.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalArgumentException(
                    "Invalid username or password"
            );
        }

        boolean passwordMatches = passwordEncoder.matches(
                request.password(),
                user.getPassword()
        );

        // Handle incorrect password.
        if (!passwordMatches) {

            int newFailedLoginCount =
                    user.getFailedLoginCount() + 1;

            user.setFailedLoginCount(newFailedLoginCount);

            // Lock the account when the configured threshold is reached.
            if (newFailedLoginCount >= maxFailedAttempts) {
                user.setStatus(AccountStatus.LOCKED);
            }

            userRepository.save(user);

            throw new IllegalArgumentException(
                    "Invalid username or password"
            );
        }

        // Successful login resets failed login attempts.
        user.setFailedLoginCount(0);

        // Record the latest successful login time.
        user.setLastLogin(LocalDateTime.now());

        userRepository.save(user);

        // Generate JWT only after successful authentication.
        String token = jwtService.generateToken(
                user.getUsername()
        );

        return new LoginResponse(token);
    }
}