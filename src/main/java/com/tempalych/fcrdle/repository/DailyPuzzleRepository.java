package com.tempalych.fcrdle.repository;

import com.tempalych.fcrdle.model.DailyPuzzle;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface DailyPuzzleRepository extends CrudRepository<DailyPuzzle, Long> {
    DailyPuzzle getByDate(LocalDate date);
}
