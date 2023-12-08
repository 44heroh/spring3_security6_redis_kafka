package com.example.spring3security6docker.dao.entity;

import com.example.spring3security6docker.dto.request.WeatherCreateRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "weather", schema = "courses")
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Weather extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1435515995276255188L;

    @Column(name = "temperature")
    private double temperature;

    @Column(name = "clouds")
    private double clouds;

    @Column(name = "visibility")
    private int visibility;

    @Column(name = "pop")
    private double pop;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "city_weathers",
            joinColumns = {@JoinColumn(name = "weather_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "city_id", referencedColumnName = "id")},
            schema = "courses"
    )
    private Set<City> cities;

    @Column(name = "date")
    private Date date;

    public Weather(WeatherCreateRequestDto weatherCreateRequestDto) {
        this.temperature = weatherCreateRequestDto.getTemperature();
        this.clouds = weatherCreateRequestDto.getClouds();
        this.visibility = weatherCreateRequestDto.getVisibility();
        this.pop = weatherCreateRequestDto.getPop();
        this.cities = weatherCreateRequestDto.getCities();
        this.date = weatherCreateRequestDto.getDate();
        this.setCreated(new Date());
        this.setUpdated(new Date());
        this.setStatus(Status.ACTIVE);
    }
}
