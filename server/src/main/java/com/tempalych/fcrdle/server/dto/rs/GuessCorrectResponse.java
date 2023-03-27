package com.tempalych.fcrdle.server.dto.rs;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GuessCorrectResponse extends GuessResponse {
    private final String city;

    @Builder
    public GuessCorrectResponse(boolean isCorrect, String league,
                                Integer stadiumCapacity, String stadiumName,
                                String city) {
        super(isCorrect, league, stadiumCapacity, stadiumName);
        this.city = city;
    }
}
