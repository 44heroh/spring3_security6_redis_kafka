package com.example.spring3security6docker.rest.controller;

import com.example.spring3security6docker.dao.entity.City;
import com.example.spring3security6docker.dao.entity.Weather;
import com.example.spring3security6docker.dto.CoordDto;
import com.example.spring3security6docker.dto.request.CityCreateRequestDto;
import com.example.spring3security6docker.dto.request.WeatherCreateRequestDto;
import com.example.spring3security6docker.dto.response.open_weather.ResponseListOpenWeather;
import com.example.spring3security6docker.dto.response.open_weather.ResponseOpenWeather;
import com.example.spring3security6docker.service.CityService;
import com.example.spring3security6docker.service.OpenWeatherService;
import com.example.spring3security6docker.service.WeatherService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final OpenWeatherService openWeatherService;
    private final CityService cityService;
    private final WeatherService weatherService;
    private static int count = 0;
    private final KafkaTemplate<Object, Object> template;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @GetMapping("/import")
    public ResponseEntity<Object> importWeather() {

        double lat = 55.751244;
        double lon = 37.618423;
        CoordDto coordDto = new CoordDto(lat, lon);
        ResponseEntity<ResponseOpenWeather> ans = openWeatherService.getForecast(coordDto);
        City city = cityService.save(
                new CityCreateRequestDto(
                        ans.getBody().getCity().getCoord().getLat(),
                        ans.getBody().getCity().getCoord().getLon(),
                        ans.getBody().getCity().getName()
                )
        );

        Set<City> cities = new HashSet<>();
        cities.add(city);

        Weather weather;
        for (ResponseListOpenWeather list : ans.getBody().getList()) {
            WeatherCreateRequestDto weatherDto = new WeatherCreateRequestDto(
                    list.getMain().getTemp(),
                    list.getClouds().getAll(),
                    list.getVisibility(),
                    list.getPop(),
                    cities,
                    new Date(list.getDt() * 1000L)
            );

            weather = weatherService.save(weatherDto);
        }

        return ResponseEntity.ok(ans.getBody().getCity());
    }

    @GetMapping("/import1")
    public ResponseEntity<Object> getWeatherMoscow1() {
        RestTemplate restTemplate = new RestTemplate();
        double lat = 55.751244;
        double lon = 37.618423;
        String apikey = "66f088d837791f8fae6fbcfc05fdf6a5";

        ResponseEntity<ResponseOpenWeather> response = null;
        try {
            response = restTemplate
                    .getForEntity("https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon="
                            + lon + "&appid=" + apikey + "&units=metric", ResponseOpenWeather.class);
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }

        System.out.println("city => " + response.getBody().getCity());
        System.out.println("list => " + response.getBody().getList());

//        ObjectMapper objectMapper = new ObjectMapper();
//        ResponseOpenWeather row = new ResponseOpenWeather();
//        try {
//           row = objectMapper.get(response.getBody().toString(), ResponseOpenWeather.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        System.out.println("response => " + response);
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = null;
//
//        try {
//            // Преобразовываем строку JSON в объект JsonNode
//            jsonNode = objectMapper.readTree(response.toString());
//
//            // Выводим содержимое JsonNode
//            System.out.println("Name: " + jsonNode.get("name").asText());
//            System.out.println("Age: " + jsonNode.get("age").asInt());
//            System.out.println("City: " + jsonNode.get("city").asText());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/weathers1")
    public ResponseEntity<String> getWeatherMoscow() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.openweathermap.org/data/2.5/weather";
        double lat = 55.751244;
        double lon = 37.618423;
        String apikey = "66f088d837791f8fae6fbcfc05fdf6a5";

        // Build the URL with parameters
        String fullUrl = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("appid", apikey)
                .queryParam("units", "metric")
                .build().toString();

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        // Set up the request entity with headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        System.out.println("fullUrl => " + fullUrl);

        String responseBody = null;
        try {
            // Make the HTTP GET request
            ResponseEntity<String> response = restTemplate.exchange(fullUrl, HttpMethod.GET, entity, String.class);
//            System.out.println("response => " . response.toString());
            // Process the response as needed
            responseBody = response.getBody();
            // Your logic to handle the response data
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/add-to-kafka")
    public ResponseEntity<String> pushToKafka() {
        this.template.send("programmingsharing.topic1", Instant.now().toString() + " Hello! " + count++);
        return ResponseEntity.ok("OK!");
    }
}
