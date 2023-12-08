package com.example.spring3security6docker.dto.response.open_weather;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseListWeatherOpenWeather {

    private int id;
    private String main;
    private String description;
    private String icon;
}
