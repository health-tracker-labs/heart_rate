package com.sergtm.controllers.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergtm.controllers.rest.dto.PersonDto;
import com.sergtm.service.IPersonService;

@RestController
@RequestMapping("/info")
public class InfoController {
	@Resource
	private IPersonService personService;

	@GetMapping("/persons")
	public List<PersonDto> persons() {
		return personService.findAll()
				.stream().map(PersonDto::new).collect(Collectors.toList());
	}
}
