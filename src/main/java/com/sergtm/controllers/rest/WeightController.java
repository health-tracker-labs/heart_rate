package com.sergtm.controllers.rest;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sergtm.controllers.rest.dto.WeightDto;
import com.sergtm.service.IWeightService;

@RestController
@RequestMapping("/weight")
public class WeightController {
	@Resource
	private IWeightService weightService;

	@GetMapping("/weights")
	public List<WeightDto> weights() {
		return weightService.findWeights();
	}

	@PutMapping("/{personId}")
	@ResponseStatus(HttpStatus.CREATED)
	public void weight(@PathVariable Long personId, @Valid WeightDto weightDto) {
		weightService.addWeight(personId, weightDto);
	}

	@DeleteMapping("/{weightId}")
	public void weight(@PathVariable Long weightId) {
		weightService.removeWeight(weightId);
	}
}
