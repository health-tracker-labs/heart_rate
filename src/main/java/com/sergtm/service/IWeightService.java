package com.sergtm.service;

import java.util.List;

import com.sergtm.controllers.rest.request.WeightRequest;

public interface IWeightService {
	void addWeight(Long personId, WeightRequest weightDto);
	void removeWeight(Long weightId);
	List<WeightRequest> findWeights();
}
