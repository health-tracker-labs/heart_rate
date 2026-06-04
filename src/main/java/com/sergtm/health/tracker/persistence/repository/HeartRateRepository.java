package com.sergtm.health.tracker.persistence.repository;

import com.sergtm.entities.HeartRate;
import com.sergtm.health.tracker.persistence.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeartRateRepository extends JpaRepository<HeartRate, Long>  {
    @Modifying
    @Query("delete from HeartRate hr where hr.person = :person")
    void deleteByPerson(Person person);

    @Query("select hr from HeartRate hr where hr.person.id = :personId")
    Optional<HeartRate> findByPersonId(@Param("personId") Long personId);
}
