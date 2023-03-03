package com.tempalych.fcrdle.dto.mapper;

import com.tempalych.fcrdle.dto.FootballClubDto;
import com.tempalych.fcrdle.dto.LocationDto;
import com.tempalych.fcrdle.model.FootballClub;

public class FootballClubMapper {

    public static FootballClubDto toFootballClubDto(FootballClub  footballClub) {
        return new FootballClubDto()
                .setId(footballClub.getId())
                .setName(footballClub.getName())
                .setStadiumCapacity(footballClub.getStadiumCapacity())
                .setStadiumName(footballClub.getStadiumName())
                .setCity(footballClub.getCity())
                .setLeague(footballClub.getLeague())
                .setLocation(new LocationDto()
                        .setLatitude(footballClub.getLocationLatitude())
                        .setLongitude(footballClub.getLocationLongitude()));
    }
}
