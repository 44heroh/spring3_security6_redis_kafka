package com.example.spring3security6docker.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CityCreateRequestDto {

    @NotEmpty(message = "lat can not be empty")
    @NotNull(message = "lat can not be null")
    private double lat;
    @NotEmpty(message = "lon can not be empty")
    @NotNull(message = "lon can not be null")
    private double lon;
    @NotEmpty(message = "name can not be empty")
    @NotNull(message = "name can not be null")
    private String name;
}
