package com.example.spring3security6docker.service.impl;

import com.example.spring3security6docker.dao.entity.City;
import com.example.spring3security6docker.dao.entity.Weather;
import com.example.spring3security6docker.dto.CoordDto;
import com.example.spring3security6docker.dto.PagerQueryDto;
import com.example.spring3security6docker.dto.request.CityCreateRequestDto;
import com.example.spring3security6docker.dto.request.WeatherCreateRequestDto;
import com.example.spring3security6docker.dto.response.open_weather.ResponseListOpenWeather;
import com.example.spring3security6docker.dto.response.open_weather.ResponseOpenWeather;
import com.example.spring3security6docker.pager.Pager;
import com.example.spring3security6docker.repository.WeatherRepository;
import com.example.spring3security6docker.rest.controller.CourseController;
import com.example.spring3security6docker.service.CityService;
import com.example.spring3security6docker.service.OpenWeatherService;
import com.example.spring3security6docker.service.WeatherService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class WeatherServiceImpl implements WeatherService {

    public static final String ID_COLUMN = "id";
    public static final Integer PAGE_SIZE_DEFAULT = 10;
    private final Logger logger = LoggerFactory.getLogger(CourseController.class);
    private final WeatherRepository repository;
    private final OpenWeatherService openWeatherService;
    private final CityService cityService;
    private final KafkaTemplate<String, WeatherCreateRequestDto> template;

    /**
     *
     * @param request
     * @return
     */
    @CacheEvict(cacheNames = {"weather","weathers"} , allEntries = true)
    public Weather save(WeatherCreateRequestDto request) {
        Weather weather = new Weather(request);
        return repository.save(weather);
    }

    @Cacheable(cacheNames = "weathers")
    public Page<Weather> getWeather(PagerQueryDto pagerQueryDto) {
        Pager pager = new Pager(pagerQueryDto);
        Page<Weather> ans = repository.findAll(pager.getPageRequest());
        return ans;
    }

    @Override
    public Optional<Weather> getWeatherById(Long id) throws Exception {
        Optional<Weather> weather = repository.findById(id);
        if(weather.isEmpty()) {
            throw new NoSuchElementException("id=" + id + " not found");
        }
        return weather;
    }

    /**
     *
     * @param coordDto
     */
    @CacheEvict(cacheNames = "weathers", allEntries = true)
    public void importWeather(CoordDto coordDto) {
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

        for (ResponseListOpenWeather list : ans.getBody().getList()) {
            WeatherCreateRequestDto weatherDto = new WeatherCreateRequestDto(
                    list.getMain().getTemp(),
                    list.getClouds().getAll(),
                    list.getVisibility(),
                    list.getPop(),
                    cities,
                    new Date(list.getDt() * 1000L)
            );

            logger.info("weatherDto => " + weatherDto);

            template.send("import-weather-topic", weatherDto);
        }
    }
}
