package com.tempalych.fcrdle.dto.mapper;

import com.tempalych.fcrdle.dto.DailyPuzzleDto;
import com.tempalych.fcrdle.model.DailyPuzzle;

public class DailyPuzzleMapper {
    public static DailyPuzzleDto toDailyPuzzleDto(DailyPuzzle dailyPuzzle) {
        return new DailyPuzzleDto()
                .setDate(dailyPuzzle.getDate())
                .setFootballClub(FootballClubMapper.toFootballClubDto(
                        dailyPuzzle.getFootballClub()));
    }
}
