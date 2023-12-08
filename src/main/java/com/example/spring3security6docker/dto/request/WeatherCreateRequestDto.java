package com.example.spring3security6docker.dto.request;

import com.example.spring3security6docker.dao.entity.City;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Getter
@Setter
public class WeatherCreateRequestDto {

    @NotEmpty(message = "temperature can not be empty")
    @NotNull(message = "temperature can not be null")
    private double temperature;
    @NotEmpty(message = "clouds can not be empty")
    @NotNull(message = "clouds can not be null")
    private double clouds;
    @NotEmpty(message = "visibility can not be empty")
    @NotNull(message = "visibility can not be null")
    private int visibility;
    @NotEmpty(message = "pop can not be empty")
    @NotNull(message = "pop can not be null")
    private double pop;
    @NotEmpty(message = "cities can not be empty")
    @NotNull(message = "cities can not be null")
    private Set<City> cities;
    @NotEmpty(message = "date can not be empty")
    @NotNull(message = "date can not be null")
    private Date date;

    public WeatherCreateRequestDto(
            double temperature,
            double clouds,
            int visibility,
            double pop,
            Set<City> cities,
            Date date
    ) {
        this.temperature = temperature;
        this.clouds = clouds;
        this.visibility = visibility;
        this.pop = pop;
        this.cities = cities;
        this.date = date;
    }
}
