package com.spring.recipeapp.handler;

import com.spring.recipeapp.exception.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class InvalidDataExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleInvalidArgument(MethodArgumentNotValidException ex, HttpServletRequest request) {
        final String[] messageError = {""};
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            messageError[0] += error.getDefaultMessage() + ",";
        });
        return ErrorResponseBuilder.buildErrorResponse(
                new ApiError(
                        request.getRequestURI(),
                        messageError[0],
                        HttpStatus.BAD_REQUEST,
                        LocalDateTime.now())
        );
    }

}
