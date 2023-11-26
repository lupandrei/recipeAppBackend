package com.spring.recipeapp.mapper;

import com.spring.recipeapp.dto.ingredient.IngredientDto;
import com.spring.recipeapp.entity.IngredientEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    List<IngredientEntity> toEntities(List<IngredientDto> ingredientDtos);
}
