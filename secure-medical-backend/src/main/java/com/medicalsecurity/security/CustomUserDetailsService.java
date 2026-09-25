package com.medicalsecurity.security;

import com.medicalsecurity.entity.RolePermission;
import com.medicalsecurity.entity.User;
import com.medicalsecurity.repository.RolePermissionRepository;
import com.medicalsecurity.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public CustomUserDetailsService(
            UserRepository userRepository,
            RolePermissionRepository rolePermissionRepository) {

        this.userRepository = userRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + username
                        )
                );

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        // Add the user's role.
        authorities.add(
                new SimpleGrantedAuthority(
                        "ROLE_" + user.getRole().name()
                )
        );

        // Load permissions assigned to the user's role.
        List<RolePermission> rolePermissions =
                rolePermissionRepository.findByRoleWithPermission(user.getRole());

        for (RolePermission rolePermission : rolePermissions) {

            authorities.add(
                    new SimpleGrantedAuthority(
                            rolePermission.getPermission().getName()
                    )
            );
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}