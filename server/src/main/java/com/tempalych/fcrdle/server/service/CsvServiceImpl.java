package com.tempalych.fcrdle.server.service;

import com.tempalych.fcrdle.server.dto.FootballClubDto;
import com.tempalych.fcrdle.server.model.FootballClub;
import com.tempalych.fcrdle.server.repository.FootballClubRepository;
import com.tempalych.fcrdle.server.util.CsvUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvServiceImpl implements CsvService{

    final Logger logger = LoggerFactory.getLogger(CsvServiceImpl.class);

    private final FootballClubRepository footballClubRepository;

    @Override
    public void loadFromCsv() throws FileNotFoundException {
        logger.info("Loading from CSV");
        List<FootballClubDto> footballClubs = CsvUtils.getFootballClubsFromFile();
        for (FootballClubDto footballClubDto : footballClubs) {
            footballClubRepository.save(new FootballClub()
                    .setName(footballClubDto.getName())
                    .setLeague(footballClubDto.getLeague())
                    .setStadiumCapacity(footballClubDto.getStadiumCapacity())
                    .setStadiumName(footballClubDto.getStadiumName())
                    .setCity(footballClubDto.getCity())
                    .setLocationLatitude(footballClubDto.getLocation().getLatitude())
                    .setLocationLongitude(footballClubDto.getLocation().getLongitude()));
        }
        logger.info(String.format("Loading from CSV completed: %s", footballClubs.size()));
    }

    @Override
    public void updateCsvFile() {
        //Premier League;Anfield;Liverpool;Liverpool;53,394;53°25′51″N 002°57′39″W
        String csvFileName = "Stadiums_updated.csv";
        File csvFile = new File(csvFileName);

        List<FootballClub> clubsFromDb = new ArrayList<>();
        footballClubRepository.findAll().forEach(clubsFromDb::add);

        try (PrintWriter pw = new PrintWriter(csvFile)){
            for (FootballClub fc: clubsFromDb) {
                StringBuilder csvString = new StringBuilder();
                csvString.append(fc.getLeague()).append(";");
                csvString.append(fc.getStadiumName()).append(";");
                csvString.append(fc.getName()).append(";");
                csvString.append(fc.getCity()).append(";");
                csvString.append(fc.getStadiumCapacity()).append(";");

                String locLatitude;
                if (fc.getLocationLatitude() < 0) {
                    locLatitude = "S";
                } else {
                    locLatitude = "N";
                }
                locLatitude = CsvUtils.concatCoordinates(fc.getLocationLatitude()) + locLatitude;

                String locLongitude;
                if (fc.getLocationLongitude() < 0) {
                    locLongitude = "W";
                } else {
                    locLongitude = "E";
                }
                locLongitude = CsvUtils.concatCoordinates(fc.getLocationLongitude()) + locLongitude;

                csvString.append(locLatitude).append(" ");
                csvString.append(locLongitude);

                pw.println(csvString);

//                System.out.println(fc.getLocationLatitude() + " "
//                        + fc.getLocationLongitude() + " - "
//                        + locLatitude + " "
//                        + locLongitude);
            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }

    }
}
