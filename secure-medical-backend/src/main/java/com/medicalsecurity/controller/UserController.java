package com.medicalsecurity.controller;

import com.medicalsecurity.entity.Role;
import com.medicalsecurity.entity.User;
import com.medicalsecurity.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // Constructor injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new application user.
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @RequestBody CreateUserRequest request) {

        User user = userService.createUser(
                request.username(),
                request.password(),
                request.role()
        );

        UserResponse response = new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Request DTO for user creation.
     */
    public record CreateUserRequest(
            String username,
            String password,
            Role role
    ) {
    }

    /**
     * Response DTO.
     *
     * IMPORTANT:
     * We intentionally do not return the password/hash.
     */
    public record UserResponse(
            Long id,
            String username,
            Role role
    ) {
    }
}