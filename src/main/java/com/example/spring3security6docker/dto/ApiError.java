package com.example.spring3security6docker.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ApiError {
    private final String message;
    private final OffsetDateTime dateOccurred;
}
