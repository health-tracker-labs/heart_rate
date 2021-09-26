package com.sergtm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sergtm.entities.Disease;

@Repository
public interface DiseaseRepository extends CrudRepository<Disease, Long> {
	Disease findOneByName(String name);
}
