package com.sergtm.controllers.rest.dto;

import com.sergtm.entities.Person;

public class PersonDto {
	private Long id;
	private String name;

	public PersonDto() {
	}

	public PersonDto(Person person) {
		this.id = person.getId();
		this.name = person.getName();
	}

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
}
