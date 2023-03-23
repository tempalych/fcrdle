package com.tempalych.fcrdle.server.dto.mapper;

import com.tempalych.fcrdle.server.dto.IpAddressDto;
import com.tempalych.fcrdle.server.dto.LocationDto;
import com.tempalych.fcrdle.server.model.IpAddress;

public class IpAddressMapper {
    public static IpAddressDto toIpAddressDto(IpAddress ipAddress) {
        return new IpAddressDto()
                .setIp(ipAddress.getIp())
                .setCountry(ipAddress.getCountry())
                .setRegion(ipAddress.getRegion())
                .setCity(ipAddress.getCity())
                .setLocation(new LocationDto()
                        .setLatitude(ipAddress.getLocationLatitude())
                        .setLongitude(ipAddress.getLocationLongitude()))
                .setTimezone(ipAddress.getTimezone());
    }
}
