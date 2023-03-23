package com.tempalych.fcrdle.wikidata;

import com.tempalych.fcrdle.server.model.FootballClub;
import com.tempalych.fcrdle.server.model.WikiFootballClub;
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
public class FootballClubCompare {
    WikiFootballClub wikiFootballClub;
    FootballClub dbFootballClub;
    SimilarityType similarityType;
    Integer levenshteinDistance;
    FootballClub newFc;
}