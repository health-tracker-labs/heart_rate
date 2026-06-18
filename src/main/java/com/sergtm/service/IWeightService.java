package com.sergtm.service;

import com.sergtm.health.tracker.rest.request.WeightRequest;
import com.sergtm.health.tracker.persistence.entity.Person;

import java.util.List;

public interface IWeightService extends IDeletableByPersonService {
	void addWeight(Person person, WeightRequest weightDto);
	void removeWeight(Long weightId);
	List<WeightRequest> findWeights();
}
