package com.sergtm.health.tracker.persistence.repository;

import com.sergtm.health.tracker.persistence.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
