package com.spring.recipeapp.dto.recipe;

import com.spring.recipeapp.entity.RecipeEntity;
import lombok.Data;

@Data
public class RecipeWithStatusDto {
    private RecipeEntity recipeEntity;
    private Boolean isSaved;
}
