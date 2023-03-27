package com.tempalych.fcrdle.server.util;

import com.tempalych.fcrdle.server.dto.FootballClubDto;
import com.tempalych.fcrdle.server.dto.LocationDto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvUtils {

    public static List<FootballClubDto> getFootballClubsFromFile() throws FileNotFoundException {
        System.out.println("Csv load requested");
        List<FootballClubDto> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("src/main/resources/Stadiums.csv"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!"".equals(line)) {
                    records.add(getRecordFromLine(line));
                }
            }
        }
        return records;
    }

    public static FootballClubDto getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(";");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }

        String fcLeague = values.get(0);
        String fcStadiumName = values.get(1);
        String fcName = values.get(2);
        String fcCity = values.get(3);
        Integer fcCapacity = Integer.parseInt(values.get(4).replace(",", ""));
        LocationDto fcLocation = parseLocation(values.get(5));

        return FootballClubDto.builder()
                .name(fcName)
                .stadiumName(fcStadiumName)
                .stadiumCapacity(fcCapacity)
                .league(fcLeague)
                .location(fcLocation)
                .city(fcCity)
                .build();
    }

    public static LocationDto parseLocation(String locationStr) {
        String[] coordinatesStr = locationStr.split(" ");
        double latitude = parseCoordinate(coordinatesStr[0]);
        double longitude = parseCoordinate(coordinatesStr[1]);
        return LocationDto.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    public static Double parseCoordinate(String coordinate) {
        String coordinateWithSide = coordinate
                .replace(".", "")
                .replace("°", ".")
                .replace("′", "")
                .replace("″", "");
        char side = coordinateWithSide.charAt(coordinateWithSide.length() - 1);
        double result = Double.parseDouble(coordinateWithSide.substring(0, coordinateWithSide.length() - 1));
        if ('S' == side || 'W' == side) {
            return result * -1;
        } else {
            return result;
        }
    }

    public static String concatCoordinates(Double coordinate) {
        String coordinateStr = ((Double)Math.abs(coordinate)).toString();
        int pointPosition = coordinateStr.indexOf(".");
        String integerPart = coordinateStr.substring(0, pointPosition);
        String fractionalPart = coordinateStr.substring(pointPosition + 1);

        if (fractionalPart.length() > 4) {
            fractionalPart = fractionalPart.substring(0, 4);
        } else {
            if (fractionalPart.length() < 4) {
                fractionalPart = String.format("%-4s", fractionalPart)
                        .replace(" ", "0");
            }
        }
        return integerPart + "" + "°"
                + fractionalPart.substring(0, 2) + "′"
                + fractionalPart.substring(2) + "″";
    }
}
