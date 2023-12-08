package com.example.spring3security6docker.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseCreateRequestDto {

    @NotEmpty(message = "author can not be empty")
    @NotNull(message = "author can not be null")
    private String author;
    @NotEmpty(message = "name can not be empty")
    @NotNull(message = "name can not be null")
    private String name;
}
