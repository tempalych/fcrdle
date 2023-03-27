package com.tempalych.fcrdle.server.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Comparator;

@Getter
@ToString
@Builder
public final class LocationDto implements Comparable<LocationDto> {
    private final Double latitude;
    private final Double longitude;



    private static final Comparator<LocationDto> COMPARATOR =
            Comparator.comparingDouble((LocationDto l) -> l.latitude)
                    .thenComparingDouble(l -> l.longitude);

    @Override
    public int compareTo(LocationDto o) {
        return COMPARATOR.compare(this, o);
    }
}
