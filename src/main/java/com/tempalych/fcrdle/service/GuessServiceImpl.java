package com.tempalych.fcrdle.service;

import com.tempalych.fcrdle.dto.DailyPuzzleDto;
import com.tempalych.fcrdle.dto.FootballClubDto;
import com.tempalych.fcrdle.dto.mapper.DailyPuzzleMapper;
import com.tempalych.fcrdle.dto.mapper.FootballClubMapper;
import com.tempalych.fcrdle.dto.rq.DailyPuzzleRequest;
import com.tempalych.fcrdle.dto.rq.GuessRequest;
import com.tempalych.fcrdle.dto.rs.DailyPuzzleResponse;
import com.tempalych.fcrdle.dto.rs.GuessCorrectResponse;
import com.tempalych.fcrdle.dto.rs.GuessIncorrectResponse;
import com.tempalych.fcrdle.dto.rs.GuessResponse;
import com.tempalych.fcrdle.model.DailyPuzzle;
import com.tempalych.fcrdle.model.FootballClub;
import com.tempalych.fcrdle.repository.DailyPuzzleRepository;
import com.tempalych.fcrdle.repository.FootballClubRepository;
import com.tempalych.fcrdle.util.LocationUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class GuessServiceImpl implements GuessService{

    final Logger logger = LoggerFactory.getLogger(GuessServiceImpl.class);

    private final DailyPuzzleRepository dailyPuzzleRepository;
    private final FootballClubRepository footballClubRepository;

    @Override
    public DailyPuzzleDto newDailyPuzzle(LocalDate date) {
        logger.info(String.format("New daily puzzle: date %tF", date));
        List<FootballClub> allClubs = new ArrayList<>();
        footballClubRepository.findAll().forEach(allClubs::add);
        Random rand = new Random();
        int randomIndex = rand.nextInt(allClubs.size());
        FootballClub randomClub = allClubs.get(randomIndex);
        DailyPuzzle dailyPuzzle = new DailyPuzzle()
                .setDate(date)
                .setFootballClub(randomClub);
        dailyPuzzleRepository.save(dailyPuzzle);
        logger.info("Daily puzzle added " + dailyPuzzle);
        return DailyPuzzleMapper.toDailyPuzzleDto(dailyPuzzle);
    }

    @Override
    public DailyPuzzleResponse getDailyPuzzle(DailyPuzzleRequest request) {
        logger.info("Daily puzzle request: " + request);
        DailyPuzzle dailyPuzzle = dailyPuzzleRepository.getByDate(request.getDate());
        return new DailyPuzzleResponse()
                .setDate(dailyPuzzle.getDate());
    }

    @Override
    public GuessResponse processGuess(GuessRequest request) {
        logger.info("New guess: " + request);
        FootballClub puzzleFootballClub =
                dailyPuzzleRepository.getByDate(request.getDate()).getFootballClub();

        FootballClubDto puzzleFootballClubDto =
                FootballClubMapper.toFootballClubDto(puzzleFootballClub);

        if (request.getFootballClubId().equals(puzzleFootballClubDto.getName())) {
            logger.info("Guess correct");
            return new GuessCorrectResponse()
                    .setCity(puzzleFootballClubDto.getCity())
                    .setStadiumName(puzzleFootballClubDto.getStadiumName())
                    .setLeague(puzzleFootballClubDto.getLeague())
                    .setStadiumCapacity(puzzleFootballClubDto.getStadiumCapacity())
                    .setCorrect(true);
        } else {
            FootballClub guessFootballClub =
                    footballClubRepository.findByName(request.getFootballClubId());

            FootballClubDto guessFootballClubDto =
                    FootballClubMapper.toFootballClubDto(guessFootballClub);

            Long distance = LocationUtils.distanceBetweenLocations(
                    puzzleFootballClubDto.getLocation(),
                    guessFootballClubDto.getLocation());

            String direction = LocationUtils.getDirection(
                    guessFootballClubDto.getLocation(),
                    puzzleFootballClubDto.getLocation());

            Boolean leagueIsCorrect = guessFootballClubDto.getLeague()
                    .equals(puzzleFootballClubDto.getLeague());

            Boolean guessCapacityIsLess =
                    guessFootballClub.getStadiumCapacity() < puzzleFootballClubDto.getStadiumCapacity();


            logger.info("Guess incorrect: " + distance);

            return new GuessIncorrectResponse()
                    .setDirection(direction)
                    .setHowClose(distance.intValue())
                    .setLeagueIsCorrect(leagueIsCorrect)
                    .setGuessCapacityIsLess(guessCapacityIsLess)
                    .setStadiumCapacity(guessFootballClubDto.getStadiumCapacity())
                    .setStadiumName(guessFootballClubDto.getStadiumName())
                    .setLeague(guessFootballClubDto.getLeague())
                    .setCorrect(false);
        }
    }

    @Override
    public Long findLongestDistance() {
        List<FootballClubDto> clubs = new ArrayList<>();
        footballClubRepository.findAll().forEach(footballClub ->
                clubs.add(FootballClubMapper.toFootballClubDto(footballClub)));
        long maximumDistance = 0;
        String fc1Name = "";
        String fc2Name = "";
        for (FootballClubDto fc1: clubs) {
            for (FootballClubDto fc2: clubs) {
                long distance = LocationUtils
                        .distanceBetweenLocations(fc1.getLocation(), fc2.getLocation());
                if (distance > maximumDistance) {
                    maximumDistance = distance;
                    fc1Name = fc1.getName();
                    fc2Name = fc2.getName();
                }
            }
        }
        logger.info("Found longest distance: " + fc1Name + " - " +
                fc2Name + ": " + maximumDistance + " km");
        return maximumDistance;
    }

}
