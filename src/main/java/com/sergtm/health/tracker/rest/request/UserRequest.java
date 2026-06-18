package com.sergtm.health.tracker.rest.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class UserRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
    private boolean state;
}
