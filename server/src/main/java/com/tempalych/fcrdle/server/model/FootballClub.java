package com.tempalych.fcrdle.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "football_club")
@ToString
public class FootballClub {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "club_name")
    private String name;

    @Column(name = "stadium_name")
    private String stadiumName;

    @Column(name= "league")
    private String league;

    @Column(name = "loc_latitude")
    private double locationLatitude;

    @Column(name = "loc_longitude")
    private double locationLongitude;

    @Column(name = "stadium_capacity")
    private int stadiumCapacity;

    @Column(name = "city")
    private String city;
}
