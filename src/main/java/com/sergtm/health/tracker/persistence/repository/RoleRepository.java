package com.sergtm.health.tracker.persistence.repository;

import com.sergtm.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
