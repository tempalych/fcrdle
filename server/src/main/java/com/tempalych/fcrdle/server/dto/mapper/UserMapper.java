package com.tempalych.fcrdle.server.dto.mapper;

import com.tempalych.fcrdle.server.dto.Role;
import com.tempalych.fcrdle.server.dto.UserDto;
import com.tempalych.fcrdle.server.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .login(user.getLogin())
                .password(user.getPassword())
                .role(Role.valueOf(user.getRole()))
                .token(user.getToken())
                .build();
    }
}
