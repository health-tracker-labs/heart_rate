package com.sergtm.controllers.rest.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.sergtm.OccasionLevel;
import com.sergtm.entities.Occasion;

import io.swagger.annotations.ApiModelProperty;

public class OccasionDto {
	@ApiModelProperty(hidden = true)
	private Long id;

	@ApiModelProperty(required = true)
	private OccasionLevel occasionLevel;

	@ApiModelProperty(required = true)
	private boolean convulsion;

	@ApiModelProperty(required = true, example = "yyyy-MM-ddTHH:mm:ss")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime occasionDate;

	public OccasionDto() {
	}

	public OccasionDto(Occasion occasion) {
		this.id = occasion.getId();
		this.occasionLevel = occasion.getOccasionLevel();
		this.convulsion = occasion.isConvulsion();
		this.occasionDate = occasion.getOccasionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public OccasionLevel getOccasionLevel() {
		return occasionLevel;
	}
	public void setOccasionLevel(OccasionLevel occasionLevel) {
		this.occasionLevel = occasionLevel;
	}

	public boolean isConvulsion() {
		return convulsion;
	}
	public void setConvulsion(boolean convulsion) {
		this.convulsion = convulsion;
	}

	public LocalDateTime getOccasionDate() {
		return occasionDate;
	}
	public void setOccasionDate(LocalDateTime occasionDate) {
		this.occasionDate = occasionDate;
	}
}
