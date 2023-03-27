package com.tempalych.fcrdle.server.dto.mapper;

import com.tempalych.fcrdle.server.dto.FootballClubDto;
import com.tempalych.fcrdle.server.dto.LocationDto;
import com.tempalych.fcrdle.server.model.FootballClub;

public class FootballClubMapper {

    public static FootballClubDto toFootballClubDto(FootballClub  footballClub) {
        return FootballClubDto.builder()
                .id(footballClub.getId())
                .name(footballClub.getName())
                .stadiumCapacity(footballClub.getStadiumCapacity())
                .stadiumName(footballClub.getStadiumName())
                .city(footballClub.getCity())
                .league(footballClub.getLeague())
                .location(LocationDto.builder()
                        .latitude(footballClub.getLocationLatitude())
                        .longitude(footballClub.getLocationLongitude())
                        .build())
                .build();
    }
}
