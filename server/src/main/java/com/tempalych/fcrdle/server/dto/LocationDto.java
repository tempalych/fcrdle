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
public class LocationDto implements Comparable<LocationDto> {
    Double latitude;
    Double longitude;

    @Override
    public int compareTo(LocationDto o) {
        if (getLatitude().equals(o.getLatitude()) &&
                getLongitude().equals(o.getLongitude())) {
            return 0;
        } else {
            return -1;
        }
    }
}
