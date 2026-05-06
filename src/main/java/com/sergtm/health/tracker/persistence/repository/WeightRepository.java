package com.sergtm.health.tracker.persistence.repository;

import com.sergtm.entities.Person;
import com.sergtm.entities.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface WeightRepository extends JpaRepository<Weight, Long> {
    @Modifying
    @Query("delete from Weight w where w.person = :person")
    void deleteByPerson(Person person);
}
