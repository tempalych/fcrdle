package com.tempalych.fcrdle.server.service;

import com.tempalych.fcrdle.server.dto.DailyPuzzleDto;
import com.tempalych.fcrdle.server.dto.FootballClubDto;
import com.tempalych.fcrdle.server.dto.GuessInfoDto;
import com.tempalych.fcrdle.server.dto.IpAddressDto;
import com.tempalych.fcrdle.server.dto.mapper.DailyPuzzleMapper;
import com.tempalych.fcrdle.server.dto.mapper.FootballClubMapper;
import com.tempalych.fcrdle.server.dto.rq.DailyPuzzleRequest;
import com.tempalych.fcrdle.server.dto.rq.GuessRequest;
import com.tempalych.fcrdle.server.dto.rs.*;
import com.tempalych.fcrdle.server.dto.rs.DailyPuzzleResponse;
import com.tempalych.fcrdle.server.model.DailyPuzzle;
import com.tempalych.fcrdle.server.model.FootballClub;
import com.tempalych.fcrdle.server.model.GuessInfo;
import com.tempalych.fcrdle.server.model.IpAddress;
import com.tempalych.fcrdle.server.repository.DailyPuzzleRepository;
import com.tempalych.fcrdle.server.repository.FootballClubRepository;
import com.tempalych.fcrdle.server.repository.GuessInfoRepository;
import com.tempalych.fcrdle.server.repository.IpAddressRepository;
import com.tempalych.fcrdle.server.util.LocationUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class GuessServiceImpl implements GuessService{

    final Logger logger = LoggerFactory.getLogger(GuessServiceImpl.class);

    private final DailyPuzzleRepository dailyPuzzleRepository;
    private final FootballClubRepository footballClubRepository;
    private final IpAddressRepository ipAddressRepository;
    private final GuessInfoRepository guessInfoRepository;

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
    public GuessResponse processGuess(GuessRequest request, HttpServletRequest httpRequest) {
        logger.info("New guess: " + request);
        FootballClub puzzleFootballClub =
                dailyPuzzleRepository.getByDate(request.getDate()).getFootballClub();

        FootballClubDto puzzleFootballClubDto =
                FootballClubMapper.toFootballClubDto(puzzleFootballClub);

        GuessResponse guessResponse;

        if (request.getFootballClubId().equals(puzzleFootballClubDto.getName())) {
            logger.info("Guess is correct");
            guessResponse = new GuessCorrectResponse()
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

            logger.info("Guess is incorrect: " + distance);

            guessResponse = new GuessIncorrectResponse()
                    .setDirection(direction)
                    .setHowClose(distance.intValue())
                    .setLeagueIsCorrect(leagueIsCorrect)
                    .setGuessCapacityIsLess(guessCapacityIsLess)
                    .setStadiumCapacity(guessFootballClubDto.getStadiumCapacity())
                    .setStadiumName(guessFootballClubDto.getStadiumName())
                    .setLeague(guessFootballClubDto.getLeague())
                    .setCorrect(false);
        }

        try {
            GuessInfoDto guessInfoDto = new GuessInfoDto()
                    .setPlatform(httpRequest.getHeader("sec-ch-ua-platform"))
                    .setMobile(httpRequest.getHeader("sec-ch-ua-mobile"))
                    .setLanguage(httpRequest.getHeader("accept-language"))
                    .setGuessClub(request.getFootballClubId())
                    .setUserId(request.getUserId())
                    .setCorrect(guessResponse.isCorrect())
                    .setIpAddress(new IpAddressDto().setIp(httpRequest.getRemoteAddr()));

            Mono<GuessInfoDto> guessInfoMono = Mono.just(guessInfoDto);
            guessInfoMono.subscribeOn(Schedulers.parallel())
                    .subscribe(this::saveGuessInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return guessResponse;
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

    private void saveGuessInfo(GuessInfoDto guess) {
        String ip = guess.getIpAddress().getIp();
        IpAddress ipAddress = ipAddressRepository.findByIp(ip);
        if (ipAddress == null) {
            ipAddress = new IpAddress().setIp(ip);
            ipAddressRepository.save(ipAddress);

            String url = String.format("http://ip-api.com/json/%s", ip);
            WebClient ipRequestClient = WebClient.create(url);

            Mono<IpApiResponse> ipAddressMono =
                    ipRequestClient.get().exchangeToMono(response -> {
                        if (response.statusCode().equals(HttpStatus.OK)) {
                            return response.bodyToMono(IpApiResponse.class);
                        } else {
                            return response.createException()
                                    .flatMap(Mono::error);
                        }
                    });
            ipAddressMono.subscribe(el -> {
                IpAddress updateIpAddress = ipAddressRepository.findByIp(ip);
                updateIpAddress.setCountry(el.getCountry())
                            .setRegion(el.getRegion())
                            .setCity(el.getCity())
                            .setLocationLatitude(el.getLat())
                            .setLocationLongitude(el.getLon())
                            .setTimezone(el.getTimezone());
                    ipAddressRepository.save(updateIpAddress);
                });
        }
        GuessInfo guessInfo = new GuessInfo()
                .setIpAddress(ipAddress)
                .setGuessClub(guess.getGuessClub())
                .setLanguage(guess.getLanguage())
                .setMobile(guess.getMobile())
                .setPlatform(guess.getPlatform())
                .setCorrect(guess.getCorrect())
                .setUserId(guess.getUserId())
                .setRequestTime(LocalDateTime.now());
        guessInfoRepository.save(guessInfo);
    }
}
