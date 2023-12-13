package com.example.spring3security6docker.service;

import com.example.spring3security6docker.dao.entity.Weather;
import com.example.spring3security6docker.dto.CoordDto;
import com.example.spring3security6docker.dto.PagerQueryDto;
import com.example.spring3security6docker.dto.request.WeatherCreateRequestDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface WeatherService {

    public Weather save(WeatherCreateRequestDto request);

    public Page<Weather> getWeather(PagerQueryDto pagerQueryDto);

    public Optional<Weather> getWeatherById(Long id) throws Exception;

    public void importWeather(CoordDto coordDto);
}
