package com.tempalych.fcrdle.server.dto.rs;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GuessIncorrectResponse extends GuessResponse {
    private final String direction;
    private final Integer howClose;
    private final Boolean guessCapacityIsLess;
    private final Boolean leagueIsCorrect;

    @Builder
    public GuessIncorrectResponse(boolean isCorrect, String league,
                                  Integer stadiumCapacity, String stadiumName,
                                  String direction, Integer howClose,
                                  Boolean guessCapacityIsLess,
                                  Boolean leagueIsCorrect) {
        super(isCorrect, league, stadiumCapacity, stadiumName);
        this.direction = direction;
        this.howClose = howClose;
        this.guessCapacityIsLess = guessCapacityIsLess;
        this.leagueIsCorrect = leagueIsCorrect;
    }
}
