package com.example.spring3security6docker.service.impl;

import com.example.spring3security6docker.config.PageConfig;
import com.example.spring3security6docker.dao.entity.City;
import com.example.spring3security6docker.dto.PagerQueryDto;
import com.example.spring3security6docker.dto.request.CityCreateRequestDto;
import com.example.spring3security6docker.pager.Pager;
import com.example.spring3security6docker.repository.CityRepository;
import com.example.spring3security6docker.rest.controller.CourseController;
import com.example.spring3security6docker.service.CityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CityServiceImpl implements CityService {

    public static final String ID_COLUMN = PageConfig.COLUMN_SORT_DEFAULT;
    public static final Integer PAGE_SIZE_DEFAULT = PageConfig.PAGE_SIZE_DEFAULT;
    private final Logger logger = LoggerFactory.getLogger(CourseController .class);
    private final CityRepository repository;

    /**
     *
     * @param request
     * @return
     */
    public City save(CityCreateRequestDto request) {
        Optional<City> ansCity = repository.findByParams(request.getLat(), request.getLon(), request.getName());
        boolean exists = !ansCity.isEmpty();
        if(exists){
            return ansCity.orElseThrow(() -> new RuntimeException("Error: City is not found."));
//            throw new IllegalArgumentException("City with : " + request.getName() +", " + request.getLat() +", " + request.getLon() + " already exists");
        }

        City city = new City(request);

        return repository.save(city);
    }

    /**
     *
     * @param pagerQueryDto
     * @return
     */
    @Cacheable(cacheNames = "weathes")
    public Page<City> getCities(PagerQueryDto pagerQueryDto) {
        Pager pager = new Pager(pagerQueryDto);
        Page<City> ans = repository.findAll(pager.getPageRequest());
        return ans;
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Optional<City> getCityById(Long id) throws Exception {
        Optional<City> city = repository.findById(id);
        if(city.isEmpty()) {
            throw new NoSuchElementException("id=" + id + " not found");
        }
        return city;
    }
}
