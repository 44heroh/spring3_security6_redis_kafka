package com.example.spring3security6docker.exception;

import com.example.spring3security6docker.dto.ApiError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.security.sasl.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> noSuchElementExceptionHandler(NoSuchElementException ex) {
        return new ResponseEntity<ApiError>(
                new ApiError(ex.getMessage(),
                        OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS)),
                HttpStatus.NOT_FOUND
        );
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ApiError> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<ApiError>(
                new ApiError(ex.getFieldErrors().stream().map(f -> f.getDefaultMessage()).findFirst().get(),
                        OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS)),
                HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ApiError> illegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<ApiError>(
                new ApiError(ex.getMessage(),
                        OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS)),
                HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ApiError> accessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<ApiError>(
                new ApiError(ex.getMessage(),
                        OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS)),
                HttpStatus.FORBIDDEN);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({MalformedJwtException.class, ExpiredJwtException.class, UnsupportedJwtException.class, AuthenticationException.class})
    public ResponseEntity<ApiError> JwtException(AccessDeniedException ex) {
        return new ResponseEntity<ApiError>(
                new ApiError(ex.getMessage(),
                        OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS)),
                HttpStatus.FORBIDDEN);
    }
}
