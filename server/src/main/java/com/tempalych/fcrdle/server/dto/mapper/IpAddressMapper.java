package com.tempalych.fcrdle.server.dto.mapper;

import com.tempalych.fcrdle.server.dto.IpAddressDto;
import com.tempalych.fcrdle.server.dto.LocationDto;
import com.tempalych.fcrdle.server.model.IpAddress;

public class IpAddressMapper {
    public static IpAddressDto toIpAddressDto(IpAddress ipAddress) {
        return IpAddressDto.builder()
                .ip(ipAddress.getIp())
                .country(ipAddress.getCountry())
                .region(ipAddress.getRegion())
                .city(ipAddress.getCity())
                .location(LocationDto.builder()
                        .latitude(ipAddress.getLocationLatitude())
                        .longitude(ipAddress.getLocationLongitude())
                        .build())
                .timezone(ipAddress.getTimezone())
                .build();
    }
}
