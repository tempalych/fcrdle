package com.tempalych.fcrdle.server.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class GuessResponse {
    private final boolean isCorrect;
    private final String league;
    private final int stadiumCapacity;
    private final String stadiumName;
}
