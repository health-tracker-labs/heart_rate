package com.sergtm.controllers.rest;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sergtm.controllers.rest.dto.OccasionDto;
import com.sergtm.service.IOccasionService;

@RestController
@RequestMapping("/occasion")
public class OccasionController {
	@Resource
	private IOccasionService occasionService;

	@GetMapping
	public List<OccasionDto> occasions() {
		return occasionService.findOccasions();
	}

	@PutMapping("/{personId}")
	@ResponseStatus(HttpStatus.CREATED)
	public void occasion(@PathVariable Long personId, OccasionDto occasionDto) {
		occasionService.addOccasion(personId, occasionDto);
	}

	@DeleteMapping("/{occasionId}")
	public void occasion(@PathVariable Long occasionId) {
		occasionService.removeOccasion(occasionId);
	}
}
