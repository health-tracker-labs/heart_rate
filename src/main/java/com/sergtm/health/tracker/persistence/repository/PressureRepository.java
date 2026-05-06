package com.sergtm.health.tracker.persistence.repository;

import com.sergtm.health.tracker.persistence.entity.Pressure;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

public interface PressureRepository extends CrudRepository<Pressure, Long> {
    @Modifying
    void deleteAllByDateIn(Collection<LocalDate> dates);
}
