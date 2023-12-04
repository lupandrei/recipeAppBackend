package com.spring.recipeapp.dto.recipe;

import com.spring.recipeapp.dto.ingredient.IngredientDto;
import com.spring.recipeapp.dto.step.StepDto;
import com.spring.recipeapp.enums.Cuisine;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RecipeUpdateDto{
    @NotNull(message="Id is missing")
    private Long id;

    @Email(message="Invalid email address")
    private String email;

    @NotNull(message="Title is missing")
    @NotEmpty(message="Title is missing")
    private String title;

    @NotNull(message="Steps are missing")
    private List<StepDto> steps;

    @NotNull(message="Ingredients are missing")
    private List<IngredientDto>ingredients;

    @NotNull(message="Photo is missing")
    @NotEmpty(message="Photo is missing")
    private String photo;

    @NotNull(message="Cuisine is missing")
    @NotEmpty(message="Cuisine is missing")
    private Cuisine cuisine;

    @NotNull(message="Cook time is missing")
    @NotEmpty(message="Cook time is missing")
    private String cookTime;
}
