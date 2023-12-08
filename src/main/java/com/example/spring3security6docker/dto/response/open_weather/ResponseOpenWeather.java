package com.example.spring3security6docker.dto.response.open_weather;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseOpenWeather {

    private String cod;
    private String message;
    private int cnt;
    private List<ResponseListOpenWeather> list;
    private ResponseCityOpenWeather city;

}
