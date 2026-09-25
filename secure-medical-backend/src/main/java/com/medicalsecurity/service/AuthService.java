package com.medicalsecurity.service;

import com.medicalsecurity.dto.LoginRequest;
import com.medicalsecurity.dto.LoginResponse;
import com.medicalsecurity.entity.User;
import com.medicalsecurity.repository.UserRepository;
import com.medicalsecurity.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.medicalsecurity.entity.AccountStatus;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

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

        if (!passwordMatches) {
            throw new IllegalArgumentException(
                    "Invalid username or password"
            );
        }

        String token = jwtService.generateToken(
                user.getUsername()
        );

        return new LoginResponse(token);
    }
}