package com.spring.recipeapp.mapper;

import com.spring.recipeapp.dto.recipe.RecipeAddDto;
import com.spring.recipeapp.dto.recipe.RecipeDisplayDto;
import com.spring.recipeapp.dto.recipe.RecipeDto;
import com.spring.recipeapp.dto.recipe.RecipeWithStatusDto;
import com.spring.recipeapp.entity.RecipeEntity;
import com.spring.recipeapp.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    @Named("addDtoToRecipeEntity")
    RecipeEntity addDtoToRecipeEntity(RecipeAddDto recipeAddDto);

    @Named("toRecipeDto")
    @Mapping(source="user",target="userRecipeDisplayInformationDto")
    @Mapping(source="recipeEntity.user.profilePicture",target="userRecipeDisplayInformationDto.photo")
    @Mapping(source="reviews",target="rating",qualifiedByName = "mapToRating")
    @Mapping(source="reviews",target="countReviews",qualifiedByName = "mapToCountReviews")
    RecipeDto toRecipeDto(RecipeEntity recipeEntity);


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
    @Named("mapToCountReviews")
    default Integer mapToCountReviews(List<ReviewEntity> reviews) {
        return reviews.size();
    }
    List<RecipeDisplayDto> toDisplayDtos(List<RecipeEntity> all);

    @Named("recipeWithStatusToRecipeDto")
    @Mapping(source = "recipeEntity.id", target = "id")
    @Mapping(source=  "recipeEntity.user.email",target="userRecipeDisplayInformationDto.email")
    @Mapping(source="recipeEntity.user.profilePicture",target="userRecipeDisplayInformationDto.photo")
    @Mapping(source="recipeEntity.user.firstName",target="userRecipeDisplayInformationDto.firstName")
    @Mapping(source="recipeEntity.user.lastName",target="userRecipeDisplayInformationDto.lastName")
    @Mapping(source = "recipeEntity.title", target = "title")
    @Mapping(source = "recipeEntity.steps", target = "steps")
    @Mapping(source = "recipeEntity.ingredients", target = "ingredients")
    @Mapping(source = "recipeEntity.photo", target = "photo")
    @Mapping(source = "recipeEntity.cuisine", target = "cuisine")
    @Mapping(source = "recipeEntity.cookTime", target = "cookTime")
    @Mapping(source = "recipeEntity.reviews", target = "rating", qualifiedByName = "mapToRating")
    @Mapping(source = "recipeEntity.reviews", target = "countReviews", qualifiedByName = "mapToCountReviews")
    @Mapping(source = "isSaved", target = "isSaved")
    RecipeDto recipeWithStatusToRecipeDto(RecipeWithStatusDto recipeEntity);
}
