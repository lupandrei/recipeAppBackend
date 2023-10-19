package com.spring.recipeapp.handler;


import com.spring.recipeapp.exception.ApiError;
import com.spring.recipeapp.exception.UserAlreadyExistsException;
import com.spring.recipeapp.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class UserServiceExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex,
                                                              HttpServletRequest request) {
        return ErrorResponseBuilder.buildErrorResponse(
                new ApiError(
                        request.getRequestURI(),
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND,
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex,
                                                                   HttpServletRequest request) {
        return ErrorResponseBuilder.buildErrorResponse(
                new ApiError(
                        request.getRequestURI(),
                        ex.getMessage(),
                        HttpStatus.CONFLICT,
                        LocalDateTime.now()
                )
        );
    }

}
