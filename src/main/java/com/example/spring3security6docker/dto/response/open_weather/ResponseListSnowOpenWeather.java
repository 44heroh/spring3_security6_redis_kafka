package com.example.spring3security6docker.dto.response.open_weather;

import lombok.*;

@Data
@NoArgsConstructor
@Getter
public class ResponseListSnowOpenWeather {

    private double _3h;

    public ResponseListSnowOpenWeather(double _3h) {
        System.out.println("3h => " + _3h);
        this._3h = _3h;
    }

    public void set_3h(double _3h) {
        this._3h = _3h;
    }
}
