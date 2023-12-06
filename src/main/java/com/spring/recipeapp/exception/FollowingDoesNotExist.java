package com.spring.recipeapp.exception;

public class FollowingDoesNotExist extends RuntimeException{
    public FollowingDoesNotExist(String message) {
        super(message);
    }
}
