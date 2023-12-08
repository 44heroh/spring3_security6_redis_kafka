package com.example.spring3security6docker.dto.response.open_weather;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseListMainOpenWeather {

    private int temp;
    private double feels_like;
    private int tem_min;
    private int temp_max;
    private int pressure;
    private int sea_level;
    private int grnd_level;
    private int humidity;
    private int temp_kf;

}
