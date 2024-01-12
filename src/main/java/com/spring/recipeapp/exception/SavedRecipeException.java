package com.spring.recipeapp.exception;

public class SavedRecipeException extends RuntimeException{
    public SavedRecipeException(String message) {
        super(message);
    }
}
