package com.sergtm.service.mapper;

import com.sergtm.dto.UserDTO;
import com.sergtm.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    List<UserDTO> toUserDtos(Iterable<User> users);
    UserDTO toUserDto(User user);
}
