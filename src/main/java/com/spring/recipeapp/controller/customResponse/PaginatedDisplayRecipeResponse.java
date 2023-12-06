package com.spring.recipeapp.controller.customResponse;

import com.spring.recipeapp.dto.recipe.RecipeDisplayDto;
import lombok.Data;

import java.util.List;

@Data
public class PaginatedDisplayRecipeResponse {
    private List<RecipeDisplayDto> recipes;
    private Long numberOfRecipes;
    private Integer numberOfPages;
}
