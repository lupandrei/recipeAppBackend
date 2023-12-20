package com.spring.recipeapp.dto.user;

public record  UserRecipeDisplayInformationDto (
        String firstName,
        String lastName,
        String email,
        String photo,
        Boolean isFollowing
){

}
