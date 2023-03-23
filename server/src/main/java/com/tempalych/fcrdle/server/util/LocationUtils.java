package com.tempalych.fcrdle.server.util;

import com.tempalych.fcrdle.server.dto.LocationDto;

public class LocationUtils {

    public static Long distanceBetweenLocations(LocationDto location1, LocationDto location2) {
        double lat1 = Math.toRadians(location1.getLatitude());
        double lon1 = Math.toRadians(location1.getLongitude());
        double lat2 = Math.toRadians(location2.getLatitude());
        double lon2 = Math.toRadians(location2.getLongitude());

        double earthRadius = 6371.01;
        double distance = earthRadius *
                Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon1 - lon2));
        return Math.round(distance);
    }

    private static String arrowDirection(String direction) {
        return switch (direction) {
            case "N" -> "⬆️";
            case "NE" -> "↗️️";
            case "E" -> "➡️️️";
            case "SE" -> "↘️️";
            case "S" -> "⬇️";
            case "SW" -> "↙️";
            case "W" -> "⬅️";
            case "NW" -> "↖️";
            default -> "⏺️";
        };
    }

    private static final String[] CARDINAL = {"E", "NE", "N", "NW", "W", "SW", "S", "SE"};

    public static String getDirection(LocationDto from, LocationDto to) {
        int compass = (((int) Math.round(Math.atan2(
                to.getLatitude() - from.getLatitude(),
                to.getLongitude() - from.getLongitude())
                / (2 * Math.PI / 8))) + 8) % 8;
        return arrowDirection(CARDINAL[compass]);
    }
}
