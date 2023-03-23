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
public class GuessResponse {
    private boolean isCorrect;
    private String league;
    private Integer stadiumCapacity;
    private String stadiumName;
}
