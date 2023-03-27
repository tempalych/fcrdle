package com.tempalych.fcrdle.utils;

import com.tempalych.fcrdle.server.FcrdleApplication;
import com.tempalych.fcrdle.server.dto.LocationDto;
import com.tempalych.fcrdle.server.util.LocationUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = FcrdleApplication.class)
@AutoConfigureMockMvc
public class LocationUtilsTest {
    @Test
    void getDirection() {
        LocationDto locationFrom = LocationDto
                .builder()
                .latitude(43.2408D)
                .longitude(39.5722D)
                .build(); // Sochi

        LocationDto locationTo = LocationDto.builder()
                .latitude(55.4729D)
                .longitude(37.3335D)
                .build(); // Moscow

        assertEquals("⬆️",
                LocationUtils.getDirection(locationFrom, locationTo));
    }
}
