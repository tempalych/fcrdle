package com.tempalych.fcrdle.server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
public class FootballClubDto implements Comparable<FootballClubDto> {
    private Long id;
    private String name;
    private String stadiumName;
    private String league;
    private LocationDto location;
    private Integer stadiumCapacity;
    private String city;

    @Override
    public int compareTo(FootballClubDto o) {
        if (getName().equals(o.getName()) &&
                getStadiumName().equals(o.getStadiumName()) &&
                getLeague().equals(o.getLeague()) &&
                getCity().equals(o.getCity()) &&
                this.getStadiumCapacity().equals(o.getStadiumCapacity()) &&
                getLocation().compareTo(o.getLocation()) == 0
        ) {
            return 0;
        } else {
            return -1;
        }
    }
}
