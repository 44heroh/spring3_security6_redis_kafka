package com.example.spring3security6docker.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CoordDto {

    @NotEmpty(message = "lat can not be empty")
    @NotNull(message = "lat can not be null")
    private Double lat;
    @NotEmpty(message = "lon can not be empty")
    @NotNull(message = "lon can not be null")
    private Double lon;
}
