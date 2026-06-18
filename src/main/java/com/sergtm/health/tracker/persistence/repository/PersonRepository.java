package com.sergtm.health.tracker.persistence.repository;

import com.sergtm.health.tracker.persistence.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    @Modifying
    @Query("delete from Person p where p.id = :personId")
    void deleteByPerson(Long personId);

    @Query("select p from Employee e "
            + " join e.patients pat "
            + " join pat.person p "
            + " where e.id = :employeeId")
    List<Person> findPersonsByEmployeeId(Long employeeId);
}
