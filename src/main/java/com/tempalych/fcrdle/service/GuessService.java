package com.tempalych.fcrdle.service;

import com.tempalych.fcrdle.dto.DailyPuzzleDto;
import com.tempalych.fcrdle.dto.rq.DailyPuzzleRequest;
import com.tempalych.fcrdle.dto.rq.GuessRequest;
import com.tempalych.fcrdle.dto.rs.DailyPuzzleResponse;
import com.tempalych.fcrdle.dto.rs.GuessResponse;

import java.time.LocalDate;

public interface GuessService {
    DailyPuzzleDto newDailyPuzzle(LocalDate date);
    DailyPuzzleResponse getDailyPuzzle(DailyPuzzleRequest request);
    GuessResponse processGuess(GuessRequest request);
    Long findLongestDistance();
}
