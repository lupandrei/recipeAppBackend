package com.spring.recipeapp.dto.recipe;

import com.spring.recipeapp.dto.ingredient.IngredientDto;
import com.spring.recipeapp.dto.step.StepDto;
import com.spring.recipeapp.enums.Cuisine;
import lombok.Data;

import java.util.List;


@Data
public class RecipeAddedDto {
    private Long id;
    private String email;
    private String title;
    private List<StepDto> steps;
    private List<IngredientDto>ingredients;
    private String photo;
    private Cuisine cuisine;
    private String cooktime;
}
