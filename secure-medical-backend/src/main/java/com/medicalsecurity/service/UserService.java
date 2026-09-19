package com.medicalsecurity.service;

import com.medicalsecurity.entity.Role;
import com.medicalsecurity.entity.User;
import com.medicalsecurity.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor injection
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates a new user.
     *
     * @param username the username
     * @param rawPassword the password entered by the user
     * @param role the user's application role
     * @return the saved user
     */
    public User createUser(String username, String rawPassword, Role role) {

        // Check whether the username is already registered
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Hash the password before storing it
        String hashedPassword = passwordEncoder.encode(rawPassword);

        // Create the User entity
        User user = new User(username, hashedPassword, role);

        // Save the user to MySQL
        return userRepository.save(user);
    }
}