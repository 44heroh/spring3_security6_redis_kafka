package com.example.spring3security6docker.dto.response.open_weather;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseCityCoordOpenWeather {

    private double lat;
    private double lon;
}
