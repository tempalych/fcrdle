package com.tempalych.fcrdle.server.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Comparator;

@Getter
@ToString
@Builder
public final class FootballClubDto implements Comparable<FootballClubDto> {
    private final long id;
    private final String name;
    private final String stadiumName;
    private final String league;
    private final LocationDto location;
    private final int stadiumCapacity;
    private final String city;

    private static final Comparator<FootballClubDto> COMPARATOR =
            Comparator.comparing((FootballClubDto fc) -> fc.name)
                    .thenComparing(fc -> fc.stadiumName)
                    .thenComparing(fc -> fc.league)
                    .thenComparing(fc -> fc.city)
                    .thenComparing(fc -> fc.stadiumCapacity)
                    .thenComparing(fc -> fc.location);

    @Override
    public int compareTo(FootballClubDto o) {
        return COMPARATOR.compare(this, o);
    }
}
