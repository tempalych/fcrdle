package com.tempalych.fcrdle.server.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@Builder
public final class DailyPuzzleDto {
    private final FootballClubDto footballClub;
    private final LocalDate date;
}
