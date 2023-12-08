package com.example.spring3security6docker.service;

import com.example.spring3security6docker.dao.entity.City;
import com.example.spring3security6docker.dao.entity.Course;
import com.example.spring3security6docker.dto.PagerQueryDto;
import com.example.spring3security6docker.dto.request.CityCreateRequestDto;
import com.example.spring3security6docker.dto.request.CourseCreateRequestDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CityService {

    public City save(CityCreateRequestDto request);

    public Page<City> getCities(PagerQueryDto pagerQueryDto);

    public Optional<City> getCityById(Long id) throws Exception;
}
