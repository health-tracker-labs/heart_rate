package com.sergtm.health.tracker.persistence.repository;

import com.sergtm.health.tracker.persistence.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
