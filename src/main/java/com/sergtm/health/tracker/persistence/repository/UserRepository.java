package com.sergtm.health.tracker.persistence.repository;

import com.sergtm.health.tracker.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByUsername(String userName);
}
