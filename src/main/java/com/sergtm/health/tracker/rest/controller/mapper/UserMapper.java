package com.sergtm.health.tracker.rest.controller.mapper;

import com.sergtm.health.tracker.persistence.entity.User;
import com.sergtm.health.tracker.rest.response.UserResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface UserMapper {
    List<UserResponse> toResponses(Iterable<User> users);
    UserResponse toResponse(User user);
}
