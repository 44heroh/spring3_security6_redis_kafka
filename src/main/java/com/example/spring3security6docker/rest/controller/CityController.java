package com.example.spring3security6docker.rest.controller;

import com.example.spring3security6docker.dao.entity.City;
import com.example.spring3security6docker.dao.entity.Weather;
import com.example.spring3security6docker.dto.PagerQueryDto;
import com.example.spring3security6docker.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Page<City>> getAllCity(
            @RequestBody PagerQueryDto pagerQueryDto
    ) throws ParseException {
        Page<City> cities = cityService.getCities(pagerQueryDto);

        return ResponseEntity.ok(cities);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Optional<City>> getWeatherById(
            @PathVariable Long id
    ) throws Exception {
        Optional<City> city = null;

        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        try {
            city = cityService.getCityById(id);
        } catch (NoSuchElementException e) { // если объект не будет найден
            throw new Exception(e.getMessage());
        }

        return ResponseEntity.ok(city);
    }
}
