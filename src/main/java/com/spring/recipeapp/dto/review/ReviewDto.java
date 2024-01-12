package com.spring.recipeapp.dto.review;


import com.spring.recipeapp.dto.user.UserRecipeDisplayInformationDto;

public record ReviewDto (
        Long id,
    UserRecipeDisplayInformationDto user,
    String reviewText,
    Double rating
){
}
