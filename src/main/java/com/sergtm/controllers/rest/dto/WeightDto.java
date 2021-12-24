package com.sergtm.controllers.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.sergtm.entities.Weight;

import io.swagger.annotations.ApiModelProperty;

public class WeightDto {
	@ApiModelProperty(hidden = true)
	private Long id;

	@DecimalMin("1")
	@DecimalMax("999.999")
	@ApiModelProperty(required = true)
	private BigDecimal weight;

	@ApiModelProperty(required = true, example = "yyyy-MM-dd")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate date;

	public WeightDto() {
	}

	public WeightDto(Weight weight) {
		this.id = weight.getId();
		this.date = weight.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		this.weight = weight.getWeight();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
}
