package com.sergtm.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sergtm.OccasionLevel;
import com.sergtm.service.IOccasionService;

@RestController
@RequestMapping("/occasion")
public class OccasionController {
	@Resource
	private IOccasionService occasionService;

	@PutMapping(path = "occasion.json")
	@ResponseStatus(HttpStatus.CREATED)
	public void occasion(OccasionLevel occasionLevel, boolean convulsion, Date occasionDate) {
		LocalDateTime ldt = LocalDateTime.ofInstant(Optional
				.ofNullable(occasionDate).orElse(new Date()).toInstant(),
				ZoneId.systemDefault());
		occasionService.addOccasion(occasionLevel, convulsion, ldt);
	}

}
