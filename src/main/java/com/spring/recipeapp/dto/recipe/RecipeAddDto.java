package com.spring.recipeapp.dto.recipe;

import com.spring.recipeapp.dto.ingredient.IngredientDto;
import com.spring.recipeapp.dto.step.StepDto;
import com.spring.recipeapp.enums.Cuisine;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;



public record RecipeAddDto (
        @Email(message="Invalid email address") String email,
    @NotNull(message="Title is missing") @NotEmpty(message="Title is missing") String title,
    @NotNull(message="Steps are missing") List<StepDto>steps,
        @NotNull(message="Ingredients are missing") List<IngredientDto>ingredients,

        @NotNull(message="Photo is missing") @NotEmpty(message="Photo is missing") String photo,
        @NotNull(message="Cuisine is missing")Cuisine cuisine,
        @NotNull(message="Cook time is missing") @NotEmpty(message="Cook time is missing")String cookTime
) {

}
