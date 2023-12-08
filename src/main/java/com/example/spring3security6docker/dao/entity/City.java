package com.example.spring3security6docker.dao.entity;

import com.example.spring3security6docker.dto.request.CityCreateRequestDto;
import com.example.spring3security6docker.dto.request.CourseCreateRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "city", schema = "courses")
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class City extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1435515995276255188L;

    @Column(name = "name")
    private String name;

    @Column(name = "lat")
    private double lat;

    @Column(name = "lon")
    private double lon;

    public City(CityCreateRequestDto cityCreateRequestDto) {
        this.lat = cityCreateRequestDto.getLat();
        this.lon = cityCreateRequestDto.getLon();
        this.name = cityCreateRequestDto.getName();
        this.setCreated(new Date());
        this.setUpdated(new Date());
        this.setStatus(Status.ACTIVE);
    }
}
