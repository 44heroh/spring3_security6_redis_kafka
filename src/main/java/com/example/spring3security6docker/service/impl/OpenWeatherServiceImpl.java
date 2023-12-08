package com.example.spring3security6docker.service.impl;

import com.example.spring3security6docker.dto.CoordDto;
import com.example.spring3security6docker.dto.response.open_weather.ResponseOpenWeather;
import com.example.spring3security6docker.service.OpenWeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenWeatherServiceImpl implements OpenWeatherService {

    private final String urlForecast = "https://api.openweathermap.org/data/2.5/forecast";
    private final String apikey = "66f088d837791f8fae6fbcfc05fdf6a5";

    /**
     *
     * @param coord
     * @return
     */
    public ResponseEntity<ResponseOpenWeather> getForecast(CoordDto coord) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResponseOpenWeather> response = null;

        try {
            response = restTemplate
                    .getForEntity(urlForecast + "?lat=" + coord.getLat() + "&lon="
                            + coord.getLon() + "&appid=" + apikey + "&units=metric", ResponseOpenWeather.class);
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }

        return response;

    }
}
