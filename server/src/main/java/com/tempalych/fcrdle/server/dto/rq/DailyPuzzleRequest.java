package com.tempalych.fcrdle.server.dto.rq;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
public class DailyPuzzleRequest {
    private LocalDate date;
    private String userId;
}
