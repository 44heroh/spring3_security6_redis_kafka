package com.example.spring3security6docker.dto.response.open_weather;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseListOpenWeather {

    private long dt;
    private ResponseListMainOpenWeather main;
    private List<ResponseListWeatherOpenWeather> weather;
    private ResponseListCloudsOpenWeather clouds;
    private ResponseListWindOpenWeather wind;
    private int visibility;
    private double pop;
    private ResponseListSnowOpenWeather snow;
    private ResponseListSysOpenWeather sys;
    private String dt_txt;
}
