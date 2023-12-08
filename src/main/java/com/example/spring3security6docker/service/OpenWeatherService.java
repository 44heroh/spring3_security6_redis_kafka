package com.example.spring3security6docker.service;

import com.example.spring3security6docker.dto.CoordDto;
import com.example.spring3security6docker.dto.response.open_weather.ResponseOpenWeather;
import org.springframework.http.ResponseEntity;

public interface OpenWeatherService {

    public ResponseEntity<ResponseOpenWeather> getForecast(CoordDto coord);
}
