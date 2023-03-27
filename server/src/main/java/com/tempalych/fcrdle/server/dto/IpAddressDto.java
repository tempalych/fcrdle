package com.tempalych.fcrdle.server.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public final class IpAddressDto {
    private final Long id;
    private final String ip;
    private final String country;
    private final String region;
    private final String city;
    private final LocationDto location;
    private final String timezone;
}
