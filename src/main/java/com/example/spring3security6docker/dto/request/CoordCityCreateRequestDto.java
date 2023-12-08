package com.example.spring3security6docker.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CoordCityCreateRequestDto {

    private Double lat;
    private Double lon;
}
