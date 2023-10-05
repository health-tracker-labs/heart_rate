package com.sergtm.controllers.rest.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequest {
    @NotNull
    private String firstName;
    private String middleName;
    @NotNull
    private String secondName;
    @NotNull
    private String userName;
    private String email;
}
