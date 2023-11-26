package com.spring.recipeapp.exception;

public class RecipeNotFoundException extends RuntimeException{

    public RecipeNotFoundException(String message) {
        super(message);
    }
}
