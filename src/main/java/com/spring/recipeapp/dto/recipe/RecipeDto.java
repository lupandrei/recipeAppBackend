package com.spring.recipeapp.dto.recipe;

import com.spring.recipeapp.dto.ingredient.IngredientDto;
import com.spring.recipeapp.dto.step.StepDto;
import com.spring.recipeapp.dto.user.UserRecipeDisplayInformationDto;
import com.spring.recipeapp.enums.Cuisine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto {
    private Integer id;
    private String title;
    private List<StepDto> steps;
    private List<IngredientDto> ingredients;
    private String photo;
    private Cuisine cuisine;
    private String cookTime;
    private Double rating;
    private Integer countReviews;
    private UserRecipeDisplayInformationDto userRecipeDisplayInformationDto;
    private Boolean isSaved;
}
