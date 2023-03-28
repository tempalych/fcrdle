package com.tempalych.fcrdle.server.dto.rs;

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
public class IpApiResponse {
    private String status; // "success"
    private String country;  // "Montenegro"
    private String countryCode; // "ME"
    private String region; //"16"
    private String regionName; // "Podgorica"
    private String city; // "Podgorica"
    private String zip; // ""
    private double lat; // 42.4411
    private double lon; // 19.2632
    private String timezone; // "Europe/Podgorica"
    private String isp; // "Internet Crna Gora"
    private String org; // ""
    private String as; // "AS8585 Crnogorski Telekom a.d.Podgorica"
    private String query ; // "77.222.25.94"
}
