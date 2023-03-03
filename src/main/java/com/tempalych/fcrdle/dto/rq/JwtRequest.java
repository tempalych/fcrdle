package com.tempalych.fcrdle.dto.rq;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
public class JwtRequest {

    private String login;
    private String password;
}
