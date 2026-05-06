package com.sergtm.health.tracker.persistence.repository;

import com.sergtm.entities.ChartType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChartTypeRepository extends JpaRepository<ChartType, Long> {
}
