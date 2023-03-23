package com.tempalych.fcrdle.server.service.security;

import com.tempalych.fcrdle.server.dto.UserDto;
import com.tempalych.fcrdle.server.dto.mapper.UserMapper;
import com.tempalych.fcrdle.server.model.User;
import com.tempalych.fcrdle.server.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    private List<UserDto> users;

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    private void loadUsers() {
        List<UserDto> userDtoList = new ArrayList<>();
        userRepository.findAll().forEach((User dbUser) ->
                userDtoList.add(UserMapper.toUserDto(dbUser)));
        this.users = userDtoList;
    }

    public Optional<UserDto> getByLogin(String login) {
        return users.stream()
                .filter(userDto -> login.equals(userDto.getLogin()))
                .findFirst();
    }
}
