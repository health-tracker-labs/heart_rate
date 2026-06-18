package com.sergtm.health.tracker.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonResponse {
	private Long id;
	private String name;
	private String country;
}
