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
public class IpAddressDto {
    private Long id;
    private String ip;
    private String country;
    private String region;
    private String city;
    private LocationDto location;
    private String timezone;
}
