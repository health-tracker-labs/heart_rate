package com.sergtm.health.tracker.rest.controller.mapper;

import com.sergtm.health.tracker.persistence.entity.User;
import com.sergtm.health.tracker.rest.request.UserRequest;
import com.sergtm.health.tracker.rest.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = BaseMapperConfig.class,
        uses = {EmployeeMapper.class})
public interface UserMapper {
    List<UserResponse> toResponses(Iterable<User> users);
    UserResponse toResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "employee", ignore = true)
    User toDomain(UserRequest userRequest);
}
