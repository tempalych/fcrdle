package com.tempalych.fcrdle.utils;

import com.tempalych.fcrdle.server.FcrdleApplication;
import com.tempalych.fcrdle.server.dto.FootballClubDto;
import com.tempalych.fcrdle.server.dto.LocationDto;
import com.tempalych.fcrdle.server.util.CsvUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = FcrdleApplication.class)
@AutoConfigureMockMvc
public class CsvUtilsTest {

    @Test
    void parseCoordinate() {
        assertEquals(50.514256D,
                CsvUtils.parseCoordinate("50°51′42.56″N"));
        assertEquals(-11.172256D,
                CsvUtils.parseCoordinate("11°17′22.56″S"));
        assertEquals(12.3419D,
                CsvUtils.parseCoordinate("12°34′19.00″E"));
        assertEquals(-41.13D,
                CsvUtils.parseCoordinate("41°13′00.00″W"));
    }

    @Test
    void parseLocation() {
        LocationDto testLocation = LocationDto.builder()
                .latitude(50.514256D)
                .longitude(12.3419D)
                .build();
        LocationDto parsedLocation = CsvUtils
                .parseLocation("50°51′42.56″N 12°34′19.00″E");
        assertEquals(0, testLocation.compareTo(parsedLocation));
    }

    @Test
    void parseStr() {
        FootballClubDto testFootballClub = FootballClubDto.builder()
                .name("Club Name")
                .stadiumName("Stadium Name")
                .league("League Name")
                .location(LocationDto.builder()
                        .latitude(53.2747D)
                        .longitude(-2.1729D)
                        .build())
                .stadiumCapacity(74310)
                .city("City")
                .build();
        FootballClubDto parsedFootballClub = CsvUtils
                .getRecordFromLine("League Name;Stadium Name;Club Name;City;74,310;53°27′47″N 002°17′29″W");

        assertEquals(0, testFootballClub.compareTo(parsedFootballClub));
    }
}
