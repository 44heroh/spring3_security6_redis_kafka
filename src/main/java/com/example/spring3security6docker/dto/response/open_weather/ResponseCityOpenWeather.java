package com.example.spring3security6docker.dto.response.open_weather;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseCityOpenWeather {

    private Long id;
    private String name;
    private ResponseCityCoordOpenWeather coord;
    private String country;
    private Long population;
    private int timezone;
    private Long sunrise;
    private Long sunset;
}
