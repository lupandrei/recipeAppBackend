package com.spring.recipeapp.handler;


import com.spring.recipeapp.exception.ApiError;
import com.spring.recipeapp.exception.InvalidPasswordException;
import com.spring.recipeapp.exception.RecipeNotFoundException;
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
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Object> handleInvalidPasswordException(InvalidPasswordException ex,
                                                                   HttpServletRequest request) {
        return ErrorResponseBuilder.buildErrorResponse(
                new ApiError(
                        request.getRequestURI(),
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST,
                        LocalDateTime.now()
                )
        );
    }
    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<Object> handleRecipeNotFoundException(RecipeNotFoundException ex,
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
