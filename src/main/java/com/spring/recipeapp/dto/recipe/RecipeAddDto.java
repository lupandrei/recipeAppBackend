package com.spring.recipeapp.dto.recipe;

import com.spring.recipeapp.dto.ingredient.IngredientDto;
import com.spring.recipeapp.dto.step.StepDto;
import com.spring.recipeapp.enums.Cuisine;

import java.util.List;

public record RecipeAddDto (
    String email,
    String title,
    List<StepDto>steps,
    List<IngredientDto>ingredients,
    String photo,
    Cuisine cuisine,
    String cookTime
) {

}
