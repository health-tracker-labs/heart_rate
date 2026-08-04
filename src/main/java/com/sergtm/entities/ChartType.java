package com.sergtm.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "CHART_TYPE")
public class ChartType implements IEntity {
	@Id
	@SequenceGenerator(name = "CHART_TYPE_SEQ", sequenceName = "CHART_TYPE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "CHART_TYPE_SEQ")
	private Long id;

	@Column(nullable = false)
	private String name;

	private String description;

	@Override
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
