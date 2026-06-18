package com.sergtm.health.tracker.rest.controller;

import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.health.tracker.rest.request.OccasionRequest;
import com.sergtm.health.tracker.service.IPersonService;
import com.sergtm.service.IOccasionService;
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
@RequestMapping("/occasions")
public class OccasionController {
	@Resource
	private IOccasionService occasionService;
	@Resource
	private IPersonService personService;

	@GetMapping
	public List<OccasionRequest> occasions() {
		return occasionService.findOccasions();
	}

	@PutMapping("/{personId}")
	@ResponseStatus(HttpStatus.CREATED)
	public void occasion(@PathVariable Long personId, @Valid OccasionRequest occasionRequest) {
		Person person = personService.findByIdOrThrowException(personId);
		occasionService.addOccasion(person, occasionRequest);
	}

	@DeleteMapping("/{occasionId}")
	public void occasion(@PathVariable Long occasionId) {
		occasionService.removeOccasion(occasionId);
	}
}
