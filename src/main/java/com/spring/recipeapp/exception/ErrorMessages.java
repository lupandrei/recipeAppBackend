package com.spring.recipeapp.exception;

public class ErrorMessages {
    public static final String ENTITY_NOT_FOUND_MSG = "User with email %s does not exist";
    public static final String ENTITY_ALREADY_EXISTS_MSG = "User with email %s already exists";

    public static final String RECIPE_NOT_FOUND = "Recipe with id %d does not exist";

    public static final String FOLLOWING_NOT_FOUND = "%s does not follow %s";

    public static final String SAVED_RECIPE_NOT_FOUND = "User %s did not save recipe with id %d";
}
