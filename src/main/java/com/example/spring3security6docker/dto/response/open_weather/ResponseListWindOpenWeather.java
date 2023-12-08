package com.example.spring3security6docker.dto.response.open_weather;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseListWindOpenWeather {

    private double speed;
    private double deg;
    private double gust;
}
