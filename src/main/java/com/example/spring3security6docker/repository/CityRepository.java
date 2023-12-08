package com.example.spring3security6docker.repository;

import com.example.spring3security6docker.dao.entity.City;
import com.example.spring3security6docker.dao.entity.Course;
import com.example.spring3security6docker.dao.entity.Weather;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("SELECT c FROM City c WHERE (c.lat = :lat AND c.lon = :lon) OR (c.name = :name) AND c.status = 'ACTIVE'")
    Optional<City> findByParams(
            @Param("lat") double lat,
            @Param("lon") double lon,
            @Param("name") String name
    );

    @Query("SELECT c FROM City c WHERE c.status = 'ACTIVE'")
    Page<City> findAll(Pageable pageable);
}
