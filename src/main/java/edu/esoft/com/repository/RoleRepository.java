package edu.esoft.com.repository;

import edu.esoft.com.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    boolean existsByRoleNameIgnoreCase(String roleName);
}
