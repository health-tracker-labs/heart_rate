package com.sergtm.health.tracker.rest.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class PersonRequest {
    @NotNull
    private String firstName;
    private String middleName;
    @NotNull
    private String secondName;
    private String email;
}
