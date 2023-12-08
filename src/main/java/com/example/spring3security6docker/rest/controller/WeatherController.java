package com.example.spring3security6docker.rest.controller;

import com.example.spring3security6docker.dao.entity.City;
import com.example.spring3security6docker.dao.entity.Weather;
import com.example.spring3security6docker.dto.CoordDto;
import com.example.spring3security6docker.dto.CourseDto;
import com.example.spring3security6docker.dto.PagerQueryDto;
import com.example.spring3security6docker.dto.request.CityCreateRequestDto;
import com.example.spring3security6docker.dto.request.CoordCityCreateRequestDto;
import com.example.spring3security6docker.dto.request.WeatherCreateRequestDto;
import com.example.spring3security6docker.dto.response.open_weather.ResponseListOpenWeather;
import com.example.spring3security6docker.dto.response.open_weather.ResponseOpenWeather;
import com.example.spring3security6docker.service.CityService;
import com.example.spring3security6docker.service.OpenWeatherService;
import com.example.spring3security6docker.service.WeatherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final OpenWeatherService openWeatherService;
    private final CityService cityService;
    private final WeatherService weatherService;

    @PostMapping("/import")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Object> importWeather(@Valid @RequestBody CoordCityCreateRequestDto coordCityCreateRequestDto) {
        System.out.println("coordCityCreateRequestDto => " + coordCityCreateRequestDto);
        CoordDto coordDto = new CoordDto(coordCityCreateRequestDto.getLat(), coordCityCreateRequestDto.getLon());
        ResponseEntity<ResponseOpenWeather> ans = openWeatherService.getForecast(coordDto);
        City city = cityService.save(
                new CityCreateRequestDto(
                        ans.getBody().getCity().getCoord().getLat(),
                        ans.getBody().getCity().getCoord().getLon(),
                        ans.getBody().getCity().getName()
                )
        );
        System.out.println("city => " + city.toString());
        System.out.println("city_id => " + city.getId());

        Set<City> cities = new HashSet<>();
        cities.add(city);

        System.out.println("cities => " + cities);
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

            System.out.println("weatherDto => " + weatherDto);

            weather = weatherService.save(weatherDto);
        }

        return ResponseEntity.ok(ans.getBody());
    }

    @PostMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Page<Weather>> getAllWeather(
            @RequestBody PagerQueryDto pagerQueryDto
    ) throws ParseException {
        Page<Weather> weathes = weatherService.getWeathes(pagerQueryDto);

        return ResponseEntity.ok(weathes);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Optional<Weather>> getWeatherById(
            @PathVariable Long id
    ) throws Exception {
        Optional<Weather> weather = null;

        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        try {
            weather = weatherService.getWeatherById(id);
        } catch (NoSuchElementException e) { // если объект не будет найден
            throw new Exception(e.getMessage());
        }

        return ResponseEntity.ok(weather);
    }
}
