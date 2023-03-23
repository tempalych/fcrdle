package com.tempalych.fcrdle.server.service.security;

import com.tempalych.fcrdle.server.dto.JwtAuthentication;
import com.tempalych.fcrdle.server.dto.UserDto;
import com.tempalych.fcrdle.server.dto.rq.JwtRequest;
import com.tempalych.fcrdle.server.dto.rs.JwtResponse;
import com.tempalych.fcrdle.server.exception.AuthException;
import com.tempalych.fcrdle.server.model.User;
import com.tempalych.fcrdle.server.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @PostConstruct
    private void loadRefreshStorage() {
        userRepository.findAll().forEach((User dbUser) ->
                refreshStorage.put(dbUser.getLogin(), dbUser.getToken()));
    }

    private void updateToken(String login, String token) {
        refreshStorage.put(login, token);
        userRepository.setToken(login, token);
    }


    public JwtResponse login(@NonNull JwtRequest authRequest) {
        final UserDto user = userService.getByLogin(authRequest.getLogin())
                .orElseThrow(() -> new AuthException("User not found"));

        if (user.getPassword().equals(authRequest.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            updateToken(user.getLogin(), refreshToken);
            return new JwtResponse()
                    .setAccessToken(accessToken)
                    .setRefreshToken(refreshToken);
        } else {
            throw new AuthException("Invalid password");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String savedRefreshToken = refreshStorage.get(login);
            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                final UserDto user = userService.getByLogin(login)
                        .orElseThrow(() -> new AuthException("User not found"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse().setAccessToken(accessToken);
            }
        }
        return new JwtResponse();
    }

    public JwtResponse refresh(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String savedRefreshToken = refreshStorage.get(login);
            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                final UserDto user = userService.getByLogin(login)
                        .orElseThrow(() -> new AuthException("User not found"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                updateToken(user.getLogin(), refreshToken);
                return new JwtResponse()
                        .setAccessToken(accessToken)
                        .setRefreshToken(newRefreshToken);
            }
        }
        throw new AuthException("Invalid JWT");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
