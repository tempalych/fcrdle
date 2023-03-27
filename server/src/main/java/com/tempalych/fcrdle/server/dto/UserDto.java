package com.tempalych.fcrdle.server.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public final class UserDto {
    private final String login;
    private final String password;
    private final Role role;
    private final String token;
}
