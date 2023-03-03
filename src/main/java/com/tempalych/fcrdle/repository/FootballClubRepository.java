package com.tempalych.fcrdle.repository;

import com.tempalych.fcrdle.model.FootballClub;
import org.springframework.data.repository.CrudRepository;

public interface FootballClubRepository extends CrudRepository<FootballClub, Long> {
    FootballClub findByName(String name);

}
