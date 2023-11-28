package com.spring.recipeapp.handler;

import com.spring.recipeapp.exception.ApiError;
import com.spring.recipeapp.exception.FollowingDoesNotExist;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class FollowingServiceExceptionHandler {
    @ExceptionHandler(FollowingDoesNotExist.class)
    public ResponseEntity<Object> handleRecipeNotFoundException(FollowingDoesNotExist ex,
                                                                HttpServletRequest request){
        return ErrorResponseBuilder.buildErrorResponse(
                new ApiError(
                        request.getRequestURI(),
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND,
                        LocalDateTime.now()
                )
        );
    }
}
