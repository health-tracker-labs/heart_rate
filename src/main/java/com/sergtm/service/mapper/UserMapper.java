package com.sergtm.service.mapper;

import com.sergtm.dto.UserDTO;
import com.sergtm.health.tracker.persistence.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    List<UserDTO> toUserDtos(Iterable<User> users);
    UserDTO toUserDto(User user);
}
