package com.sergtm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sergtm.entities.Weight;

public interface WeightRepository extends JpaRepository<Weight, Long> {

}
