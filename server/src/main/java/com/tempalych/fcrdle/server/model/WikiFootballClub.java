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
@Table(name = "wiki_football_club")
public class WikiFootballClub {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "club_name")
    private String name;

    @Column(name = "wiki_club_id")
    private String wikiClubId;

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
