package com.tempalych.fcrdle.server.repository;

import com.tempalych.fcrdle.server.model.FootballClub;
import org.springframework.data.repository.CrudRepository;

public interface FootballClubRepository extends CrudRepository<FootballClub, Long> {
    FootballClub findByName(String name);

}
