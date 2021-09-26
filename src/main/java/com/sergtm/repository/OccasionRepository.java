package com.sergtm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sergtm.entities.Occasion;

@Repository
public interface OccasionRepository extends CrudRepository<Occasion, Long> {
}
