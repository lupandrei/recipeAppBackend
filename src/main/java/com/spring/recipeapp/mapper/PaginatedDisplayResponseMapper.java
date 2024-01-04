package com.spring.recipeapp.mapper;


import com.spring.recipeapp.controller.customResponse.PaginatedDisplayRecipeResponse;
import com.spring.recipeapp.dto.recipe.RecipeDisplayDto;
import com.spring.recipeapp.entity.RecipeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PaginatedDisplayResponseMapper {

    @Mapping(source = "content", target = "recipes")
    @Mapping(source = "totalElements", target = "numberOfRecipes")
    @Mapping(source = "totalPages", target = "numberOfPages")
    PaginatedDisplayRecipeResponse toPaginatedDisplayRecipeResponse(Page<RecipeEntity> recipes);

    @Mapping(source = "content", target = "recipes")
    @Mapping(source = "totalElements", target = "numberOfRecipes")
    @Mapping(source = "totalPages", target = "numberOfPages")
    PaginatedDisplayRecipeResponse fromRecipeDisplayDtotoPaginatedDisplayResponse(Page<RecipeDisplayDto> recipes);
}
