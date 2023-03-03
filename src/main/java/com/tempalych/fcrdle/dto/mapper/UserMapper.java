package com.tempalych.fcrdle.dto.mapper;

import com.tempalych.fcrdle.dto.Role;
import com.tempalych.fcrdle.dto.UserDto;
import com.tempalych.fcrdle.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto()
                .setLogin(user.getLogin())
                .setPassword(user.getPassword())
                .setRole(Role.valueOf(user.getRole()))
                .setToken(user.getToken());
    }
}
