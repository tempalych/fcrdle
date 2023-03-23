package com.tempalych.fcrdle.server.dto.mapper;

import com.tempalych.fcrdle.server.dto.FootballClubDto;
import com.tempalych.fcrdle.server.dto.LocationDto;
import com.tempalych.fcrdle.server.model.FootballClub;

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
