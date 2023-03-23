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
        LocationDto locationFrom = new LocationDto()
                .setLatitude(43.2408D).setLongitude(39.5722D); // Sochi

        LocationDto locationTo = new LocationDto()
                .setLatitude(55.4729D).setLongitude(37.3335D); // Moscow

        assertEquals("⬆️",
                LocationUtils.getDirection(locationFrom, locationTo));
    }
}
