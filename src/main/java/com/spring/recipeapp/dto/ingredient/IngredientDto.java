package com.spring.recipeapp.dto.ingredient;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record IngredientDto(

        @NotNull(message = "Ingredient name is missing")
        @NotEmpty(message = "Ingredient name is missing")
        String name,
        @NotNull(message = "Ingredient quantity is missing")
        @NotEmpty(message = "Ingredient quantity is missing")
        Float quantity,
        @NotNull(message = "Ingredient unit is missing")
        @NotEmpty(message = "Ingredient unit is missing")
        String unit
) {
}
