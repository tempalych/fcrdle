package com.tempalych.fcrdle.server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
public class GuessInfoDto {
    private Long id;
    private LocalDateTime requestTime;
    private String guessClub;
    private IpAddressDto ipAddress;
    private String platform;
    private String mobile;
    private String language;
    private Boolean correct;
    private String userId;
}
