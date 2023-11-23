package com.spring.recipeapp.dto.ingredient;

public record IngredientDto(
        String name,
        Float quantity,
        String unit
) {
}
