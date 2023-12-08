package com.example.spring3security6docker.dto;

import com.example.spring3security6docker.dao.entity.City;
import com.example.spring3security6docker.dao.entity.Status;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.Set;

public class WeatherDto {

    private Long id;
    @NotNull(message = "temperature не может быть пустым")
    private String temperature;
    @NotNull(message = "clouds не может быть пустым")
    private String clouds;
    @NotNull(message = "visibility не может быть пустым")
    private int visibility;
    @NotNull(message = "pop не может быть пустым")
    private double pop;
    private Date date;
    private Set<City> cities;
    private Date created;
    private Date updated;
    private Status status;
}
