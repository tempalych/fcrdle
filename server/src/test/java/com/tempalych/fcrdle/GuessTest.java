package com.tempalych.fcrdle;

import com.tempalych.fcrdle.server.FcrdleApplication;
import com.tempalych.fcrdle.server.dto.rq.GuessRequest;
import com.tempalych.fcrdle.server.dto.rs.GuessIncorrectResponse;
import com.tempalych.fcrdle.server.dto.rs.GuessResponse;
import com.tempalych.fcrdle.server.model.DailyPuzzle;
import com.tempalych.fcrdle.server.model.FootballClub;
import com.tempalych.fcrdle.server.repository.DailyPuzzleRepository;
import com.tempalych.fcrdle.server.repository.FootballClubRepository;
import com.tempalych.fcrdle.server.service.GuessService;
import com.tempalych.fcrdle.server.service.GuessServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = FcrdleApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = {"classpath:test.properties"})
public class GuessTest {

    final Logger logger = LoggerFactory.getLogger(GuessServiceImpl.class);

    @Autowired
    DailyPuzzleRepository dailyPuzzleRepository;

    @Autowired
    FootballClubRepository footballClubRepository;

    @Autowired
    GuessService guessService;

    @AfterEach
    public void cleanUpData() {
        dailyPuzzleRepository.deleteAll();
        footballClubRepository.deleteAll();
        boolean dp = dailyPuzzleRepository.findAll().iterator().hasNext();
        boolean fc = footballClubRepository.findAll().iterator().hasNext();
        logger.info("Clean up completed: dp=" + dp + ", fc=" + fc);
    }

    @Test
    @DisplayName("Correct guess test")
    public void guessCorrectTest() {
        FootballClub footballClub = new FootballClub()
                .setName("Test FC")
                .setCity("Test City")
                .setLeague("Test League")
                .setStadiumName("Test Stadium")
                .setStadiumCapacity(10000)
                .setLocationLatitude(1.104D)
                .setLocationLongitude(-4.12D);
        footballClubRepository.save(footballClub);
        logger.info(footballClub.toString());

        LocalDate today = LocalDate.now();

        DailyPuzzle dailyPuzzle = new DailyPuzzle();
        dailyPuzzle
                .setDate(today)
                .setFootballClub(footballClub);
        dailyPuzzleRepository.save(dailyPuzzle);
        logger.info(dailyPuzzle.toString());

        GuessRequest request = GuessRequest.builder()
                .date(today)
                .footballClubId("Test FC")
                .userId("user-id")
                .build();

        GuessResponse response = guessService.processGuess(request);
        logger.info(request.toString());

        assertTrue(response.isCorrect());
        assertEquals("Test League", response.getLeague());
        assertEquals(10000, response.getStadiumCapacity());
        assertEquals("Test Stadium", response.getStadiumName());
    }

    @Test
    @DisplayName("Incorrect guess test")
    public void guessIncorrectTest() {
        FootballClub footballClub1 = new FootballClub()
                .setName("Test FC1")
                .setCity("Test City1")
                .setLeague("Test League1")
                .setStadiumName("Test Stadium1")
                .setStadiumCapacity(10000)
                .setLocationLatitude(1.104D)
                .setLocationLongitude(-4.12D);
        footballClubRepository.save(footballClub1);
        logger.info(footballClub1.toString());

        FootballClub footballClub2 = new FootballClub()
                .setName("Test FC2")
                .setCity("Test City2")
                .setLeague("Test League2")
                .setStadiumName("Test Stadium2")
                .setStadiumCapacity(11000)
                .setLocationLatitude(2.104D)
                .setLocationLongitude(-6.12D);
        footballClubRepository.save(footballClub2);
        logger.info(footballClub2.toString());

        LocalDate today = LocalDate.now();

        DailyPuzzle dailyPuzzle = new DailyPuzzle();
        dailyPuzzle
                .setDate(today)
                .setFootballClub(footballClub1);
        dailyPuzzleRepository.save(dailyPuzzle);
        logger.info(dailyPuzzle.toString());

        GuessRequest request = GuessRequest.builder()
                .date(today)
                .footballClubId("Test FC2")
                .userId("user-id")
                .build();

        GuessResponse response = guessService.processGuess(request);
        logger.info(response.toString());

        assertFalse(response.isCorrect());
        GuessIncorrectResponse incorrectResponse = (GuessIncorrectResponse) response;
        assertEquals(249, incorrectResponse.getHowClose());
        assertFalse(incorrectResponse.isGuessCapacityIsLess());
        assertFalse(incorrectResponse.isLeagueIsCorrect());
        assertEquals("↘️️", incorrectResponse.getDirection());
    }
}
