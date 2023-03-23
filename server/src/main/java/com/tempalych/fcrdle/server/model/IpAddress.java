package com.tempalych.fcrdle.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "ip_address")
public class IpAddress {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="ip")
    private String ip;

    @Column(name="country")
    private String country;

    @Column(name="region")
    private String region;

    @Column(name="city")
    private String city;

    @Column(name = "loc_latitude")
    private Double locationLatitude;

    @Column(name = "loc_longitude")
    private Double locationLongitude;

    @Column(name = "timezone")
    private String timezone;
}
