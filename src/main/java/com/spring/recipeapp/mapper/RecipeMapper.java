package com.spring.recipeapp.mapper;

import com.spring.recipeapp.dto.recipe.RecipeAddDto;
import com.spring.recipeapp.dto.recipe.RecipeDisplayDto;
import com.spring.recipeapp.dto.recipe.RecipeDto;
import com.spring.recipeapp.entity.RecipeEntity;
import com.spring.recipeapp.entity.ReviewEntity;
import com.spring.recipeapp.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    @Named("addDtoToRecipeEntity")
    RecipeEntity addDtoToRecipeEntity(RecipeAddDto recipeAddDto);

    @Named("toRecipeDto")
    @Mapping(source="user",target="email",qualifiedByName = "userToEmail")
    RecipeDto toRecipeDto(RecipeEntity recipeEntity);

    @Named("userToEmail")
    default String userToEmail(UserEntity user){
        return user.getEmail();
    }

    @Mapping(source="reviews",target = "rating", qualifiedByName = "mapToRating")
    RecipeDisplayDto toDisplayDto(RecipeEntity recipe);

    @Named("mapToRating")
    default Double mapToRating(List<ReviewEntity> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }
        double totalRating = 0.0;
        for (ReviewEntity review : reviews) {
            totalRating += review.getRating();
        }
        return totalRating / reviews.size();
    }
    List<RecipeDisplayDto> toDisplayDtos(List<RecipeEntity> all);
}
