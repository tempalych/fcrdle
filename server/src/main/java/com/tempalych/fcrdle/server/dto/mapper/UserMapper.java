package com.tempalych.fcrdle.server.dto.mapper;

import com.tempalych.fcrdle.server.dto.Role;
import com.tempalych.fcrdle.server.dto.UserDto;
import com.tempalych.fcrdle.server.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto()
                .setLogin(user.getLogin())
                .setPassword(user.getPassword())
                .setRole(Role.valueOf(user.getRole()))
                .setToken(user.getToken());
    }
}
