package com.sergtm.health.tracker.rest.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

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
