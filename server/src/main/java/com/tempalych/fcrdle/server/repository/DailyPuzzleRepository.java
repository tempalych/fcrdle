package com.tempalych.fcrdle.server.repository;

import com.tempalych.fcrdle.server.model.DailyPuzzle;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface DailyPuzzleRepository extends CrudRepository<DailyPuzzle, Long> {
    DailyPuzzle getByDate(LocalDate date);
}
