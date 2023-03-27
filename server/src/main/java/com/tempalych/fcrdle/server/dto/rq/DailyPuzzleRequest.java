package com.tempalych.fcrdle.server.dto.rq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
public final class DailyPuzzleRequest {
    private final LocalDate date;
    private final String userId;
}
