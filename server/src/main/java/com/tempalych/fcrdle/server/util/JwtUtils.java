package com.tempalych.fcrdle.server.util;

import com.tempalych.fcrdle.server.dto.JwtAuthentication;
import com.tempalych.fcrdle.server.dto.Role;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtils {
    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRole(Role.valueOf(claims.get("role", String.class)));
        jwtInfoToken.setUsername(claims.getSubject());
        return jwtInfoToken;
    }

}
