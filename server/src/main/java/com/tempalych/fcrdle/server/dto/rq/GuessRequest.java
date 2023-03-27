package com.tempalych.fcrdle.server.dto.rq;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@Builder
public final class GuessRequest {
    private final String footballClubId;
    private final LocalDate date;
    private final String userId;
}
