package com.spring.recipeapp.dto.recipe;


import lombok.Data;

@Data
public class RecipeDisplayDto {
    private Long id;
    private String title;
    private String cookTime;
    private Double rating;
    private String photo;
}
