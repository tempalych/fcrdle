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
@Table(name = "football_club")
public class FootballClub {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "club_name")
    private String name;

    @Column(name = "stadium_name")
    private String stadiumName;

    @Column(name= "league")
    private String league;

    @Column(name = "loc_latitude")
    private Double locationLatitude;

    @Column(name = "loc_longitude")
    private Double locationLongitude;

    @Column(name = "stadium_capacity")
    private Integer stadiumCapacity;

    @Column(name = "city")
    private String city;
}
