package com.tempalych.fcrdle.util;

import com.tempalych.fcrdle.dto.JwtAuthentication;
import com.tempalych.fcrdle.dto.Role;
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
