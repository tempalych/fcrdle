package com.tempalych.fcrdle.server.dto.mapper;

import com.tempalych.fcrdle.server.dto.DailyPuzzleDto;
import com.tempalych.fcrdle.server.model.DailyPuzzle;

public class DailyPuzzleMapper {
    public static DailyPuzzleDto toDailyPuzzleDto(DailyPuzzle dailyPuzzle) {
        return new DailyPuzzleDto()
                .setDate(dailyPuzzle.getDate())
                .setFootballClub(FootballClubMapper.toFootballClubDto(
                        dailyPuzzle.getFootballClub()));
    }
}
