package com.AI.core.service;

import com.AI.core.constant.PingMapAPIEndPoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;


@Slf4j
@Component
@RequiredArgsConstructor
public class BingMapAPIService {

    private String keyValue="AhR8aVvA88QOY17jnEbs73h0R-kqTEiOAVfObbcyHZJl1Bz7OEtSHeZMXrNjJEsG";
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    public double[][] getDistance(ArrayList<String> addressList){
        double[][] distanceMatrix=new double[addressList.size()+2][addressList.size()+2];
//        log.info("{}",addressList.size());
        for(int i=0;i<addressList.size();i++){
            for(int j=0;j<addressList.size();j++){
                if(i!=j){
                    distanceMatrix[i][j]=getRouteDistance(addressList.get(i),addressList.get(j));
//                    log.info("{}",distanceMatrix[i][j]);
                }else {
                    distanceMatrix[i][j]=0;
                }

            }
        }
//        for(int i=0;i<addressList.size();i++){
//            for(int j=0;j<addressList.size();j++){
//               log.info("{}",distanceMatrix[i][j]);
//
//            }
//        }

        return  distanceMatrix;

    }

    public double getRouteDistance(String location1,String location2){
            String url = PingMapAPIEndPoint.routeAPI +
                    "wayPoint.1=" + location1 +
                    "&wayPoint.2=" + location2 +
                    "&optimize=distance&distanceUnit=km&key=" + keyValue;

            String RouteJson = restTemplate.getForObject(url, String.class);
            int index=RouteJson.lastIndexOf("\"travelDistance\"");
            String distance=RouteJson.substring(index);
            int  questionMarkFirstOccurrence=distance.indexOf(":");
            int  commaFirstOccurrence=distance.indexOf(",");
            double result=Double.parseDouble(distance.substring(questionMarkFirstOccurrence+1,commaFirstOccurrence));

            return  result;
    }
}
