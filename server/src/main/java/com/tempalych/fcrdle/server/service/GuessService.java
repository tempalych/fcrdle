package com.tempalych.fcrdle.server.service;

import com.tempalych.fcrdle.server.dto.DailyPuzzleDto;
import com.tempalych.fcrdle.server.dto.rq.DailyPuzzleRequest;
import com.tempalych.fcrdle.server.dto.rq.GuessRequest;
import com.tempalych.fcrdle.server.dto.rs.DailyPuzzleResponse;
import com.tempalych.fcrdle.server.dto.rs.GuessResponse;

import java.time.LocalDate;

public interface GuessService {
    DailyPuzzleDto newDailyPuzzle(LocalDate date);
    DailyPuzzleResponse getDailyPuzzle(DailyPuzzleRequest request);
    GuessResponse processGuess(GuessRequest request);
    Long findLongestDistance();
}
