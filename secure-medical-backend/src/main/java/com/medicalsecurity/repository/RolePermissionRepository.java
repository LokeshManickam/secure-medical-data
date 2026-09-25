package com.medicalsecurity.repository;

import com.medicalsecurity.entity.Role;
import com.medicalsecurity.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    /**
     * Loads role permissions together with their Permission entities.
     *
     * JOIN FETCH prevents LazyInitializationException when
     * CustomUserDetailsService accesses permission.getName().
     */
    @Query("""
            SELECT rp
            FROM RolePermission rp
            JOIN FETCH rp.permission
            WHERE rp.role = :role
            """)
    List<RolePermission> findByRoleWithPermission(Role role);

    boolean existsByRoleAndPermission_Name(Role role, String permissionName);
}