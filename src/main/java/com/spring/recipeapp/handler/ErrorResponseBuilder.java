package com.spring.recipeapp.handler;

import com.spring.recipeapp.exception.ApiError;
import org.springframework.http.ResponseEntity;

public class ErrorResponseBuilder {
    public static ResponseEntity<Object> buildErrorResponse(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.statusCode());
    }
}
