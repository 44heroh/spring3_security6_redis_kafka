package com.example.spring3security6docker.repository;

import com.example.spring3security6docker.dao.entity.City;
import com.example.spring3security6docker.dao.entity.Course;
import com.example.spring3security6docker.dao.entity.Weather;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository  extends JpaRepository<Weather, Long> {

    @Query("SELECT c FROM Weather c WHERE c.status = 'ACTIVE'")
    Page<Weather> findAll(Pageable pageable);
}
