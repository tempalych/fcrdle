package com.tempalych.fcrdle.server.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public final class GuessInfoDto {
    private final Long id;
    private final LocalDateTime requestTime;
    private final String guessClub;
    private final IpAddressDto ipAddress;
    private final String platform;
    private final String mobile;
    private final String language;
    private final Boolean correct;
    private final String userId;
}
