package com.sergtm.health.tracker.rest.controller.mapper;

import com.sergtm.entities.Role;
import com.sergtm.health.tracker.rest.response.RoleResponse;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(config = BaseMapperConfig.class)
public interface RoleMapper {
    Set<RoleResponse> toResponses(Iterable<Role> roles);
    RoleResponse toResponse(Role role);
}
