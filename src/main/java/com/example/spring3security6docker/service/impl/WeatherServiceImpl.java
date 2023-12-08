package com.example.spring3security6docker.service.impl;

import com.example.spring3security6docker.dao.entity.Weather;
import com.example.spring3security6docker.dto.PagerQueryDto;
import com.example.spring3security6docker.dto.request.WeatherCreateRequestDto;
import com.example.spring3security6docker.pager.Pager;
import com.example.spring3security6docker.repository.WeatherRepository;
import com.example.spring3security6docker.rest.controller.CourseController;
import com.example.spring3security6docker.service.WeatherService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class WeatherServiceImpl implements WeatherService {

    public static final String ID_COLUMN = "id";
    public static final Integer PAGE_SIZE_DEFAULT = 10;
    private final Logger logger = LoggerFactory.getLogger(CourseController.class);
    private final WeatherRepository repository;

    /**
     *
     * @param request
     * @return
     */
    @CacheEvict(cacheNames = "customers", allEntries = true)
    public Weather save(WeatherCreateRequestDto request) {
        Weather weather = new Weather(request);
        return repository.save(weather);
    }

    @Cacheable(cacheNames = "weathes")
    public Page<Weather> getWeathes(PagerQueryDto pagerQueryDto) {
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
}
