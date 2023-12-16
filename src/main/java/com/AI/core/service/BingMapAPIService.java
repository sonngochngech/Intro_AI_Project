package com.AI.core.service;

import com.AI.core.GA.Configs;
import com.AI.core.constant.PingMapAPIEndPoint;

import com.AI.core.helper.Point;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;


@Slf4j
@Component
@RequiredArgsConstructor
public class BingMapAPIService {

    private final String keyValue = "AhR8aVvA88QOY17jnEbs73h0R-kqTEiOAVfObbcyHZJl1Bz7OEtSHeZMXrNjJEsG";
    private final RestTemplate restTemplate;


    public double[][] getDistance(ArrayList<String> addressList) {
        double[][] distanceMatrix = new double[addressList.size() + 2][addressList.size() + 2];
        for (int i = 0; i < addressList.size(); i++) {
            for (int j = 0; j < addressList.size(); j++) {
                if (i != j) {
                    distanceMatrix[i][j] = getRouteDistance(addressList.get(i), addressList.get(j));
                } else {
                    distanceMatrix[i][j] = 0;
                }

            }
        }
        for (int i = 0; i < addressList.size(); i++) {
            for (int j = 0; j < addressList.size(); j++) {
                log.info("{}", distanceMatrix[i][j]);

            }
        }

        return distanceMatrix;

    }

    public double getRouteDistance(String location1, String location2) {
        String url = PingMapAPIEndPoint.routeAPI +
                "wayPoint.1=" + location1 +
                "&wayPoint.2=" + location2 +
                "&optimize=distance&distanceUnit=km&key=" + keyValue;

        String RouteJson = restTemplate.getForObject(url, String.class);
        int index = RouteJson.lastIndexOf("\"travelDistance\"");
        String distance = RouteJson.substring(index);
        int questionMarkFirstOccurrence = distance.indexOf(":");
        int commaFirstOccurrence = distance.indexOf(",");
        double result = Double.parseDouble(distance.substring(questionMarkFirstOccurrence + 1, commaFirstOccurrence));

        return result;
    }

    public double[][] getDistanceByCoordinates(ArrayList<String> addressList) {
        double[][] distanceMatrix = new double[addressList.size() + 2][addressList.size() + 2];
        ArrayList<ArrayList<Double>> coordinateList = new ArrayList<>();
        for (String t : addressList) {
            coordinateList.add(getCoordinatesByAddress(t));
        }
        for (ArrayList<Double> x : coordinateList) {
            Configs.points.add(new Point(x.get(0), x.get(1)));
        }
        for (int i = 0; i < addressList.size(); i++) {
            for (int j = 0; j < addressList.size(); j++) {
                if (i != j) {
                    distanceMatrix[i][j] = getDistanceFromCoordinates(coordinateList.get(i), coordinateList.get(j));
                } else {
                    distanceMatrix[i][j] = 0;
                }

            }
        }
        for (int i = 0; i < addressList.size(); i++) {
            for (int j = 0; j < addressList.size(); j++) {

            }
        }

        return distanceMatrix;

    }

    public ArrayList<Double> getCoordinatesByAddress(String location) {
        ArrayList<Double> coordinatesList = new ArrayList<>();
        String url = PingMapAPIEndPoint.locationByAddressAPI + location + "?key=" + keyValue;
        String LocationJson = restTemplate.getForObject(url, String.class);

        int index = LocationJson.indexOf("\"coordinates\":[");
        String coordinateString = LocationJson.substring(index);

        int commaIndex = coordinateString.indexOf(",");
        double firstCoordinate = Double.parseDouble(coordinateString.substring(15, commaIndex));

        int secondIndex = coordinateString.indexOf("]");
        double secondCoordinate = Double.parseDouble(coordinateString.substring(commaIndex + 1, secondIndex));

        coordinatesList.add(firstCoordinate);
        coordinatesList.add(secondCoordinate);
        return coordinatesList;
    }

    public double getDistanceFromCoordinates(ArrayList<Double> CoordinateOne, ArrayList<Double> CoordinateTwo) {
        double latDistance = Math.toRadians(CoordinateOne.get(0) - CoordinateTwo.get(0));
        double lngDistance = Math.toRadians(CoordinateOne.get(1) - CoordinateTwo.get(1));
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(CoordinateOne.get(0))) * Math.cos(Math.toRadians(CoordinateTwo.get(0)))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return 6371 * c;


    }
}
