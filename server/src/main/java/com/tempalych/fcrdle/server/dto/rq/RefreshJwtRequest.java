package com.tempalych.fcrdle.server.dto.rq;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public final class RefreshJwtRequest {
    public final String refreshToken;
}
