package com.spring.recipeapp.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ApiError(
        String path,
        String message,
        HttpStatus statusCode,
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyyMMdd hh:mm:ss")
        LocalDateTime localDateTime
) {
}
