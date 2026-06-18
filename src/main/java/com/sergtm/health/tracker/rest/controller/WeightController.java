package com.sergtm.health.tracker.rest.controller;

import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.health.tracker.rest.request.WeightRequest;
import com.sergtm.health.tracker.service.IPersonService;
import com.sergtm.service.IWeightService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/weights")
public class WeightController {
	@Resource
	private IWeightService weightService;
	@Resource
	private IPersonService personService;

	@GetMapping
	public List<WeightRequest> weights() {
		return weightService.findWeights();
	}

	@PutMapping("/{personId}")
	@ResponseStatus(HttpStatus.CREATED)
	public void weight(@PathVariable Long personId, @Valid WeightRequest weightDto) {
		Person person = personService.findByIdOrThrowException(personId);
		weightService.addWeight(person, weightDto);
	}

	@DeleteMapping("/{weightId}")
	public void weight(@PathVariable Long weightId) {
		weightService.removeWeight(weightId);
	}
}
