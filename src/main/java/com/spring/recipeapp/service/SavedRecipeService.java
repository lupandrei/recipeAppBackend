package com.spring.recipeapp.service;

import com.spring.recipeapp.controller.customResponse.PaginatedDisplayRecipeResponse;
import com.spring.recipeapp.dto.recipe.RecipeSaveDto;
import com.spring.recipeapp.dto.recipe.RecipeSavedDto;
import com.spring.recipeapp.dto.recipe.RecipeSpec;
import com.spring.recipeapp.entity.RecipeEntity;
import com.spring.recipeapp.entity.SavedRecipeEntity;
import com.spring.recipeapp.entity.UserEntity;
import com.spring.recipeapp.exception.ErrorMessages;
import com.spring.recipeapp.exception.RecipeNotFoundException;
import com.spring.recipeapp.exception.UserNotFoundException;
import com.spring.recipeapp.mapper.PaginatedDisplayResponseMapper;
import com.spring.recipeapp.repository.RecipeRepository;
import com.spring.recipeapp.repository.SavedRecipeRepostory;
import com.spring.recipeapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SavedRecipeService {
    private final SavedRecipeRepostory savedRecipeRepostory;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final PaginatedDisplayResponseMapper paginatedDisplayResponseMapper;

    @Autowired
    public SavedRecipeService(SavedRecipeRepostory savedRecipeRepostory, UserRepository userRepository, RecipeRepository recipeRepository, PaginatedDisplayResponseMapper paginatedDisplayResponseMapper) {
        this.savedRecipeRepostory = savedRecipeRepostory;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
        this.paginatedDisplayResponseMapper = paginatedDisplayResponseMapper;
    }

    public PaginatedDisplayRecipeResponse getSavedRecipes(String email, Pageable pageable) {
        return paginatedDisplayResponseMapper.toPaginatedDisplayRecipeResponse(recipeRepository
                .findAll(RecipeSpec.recipesSavedByUser(email),pageable));
    }

    public RecipeSavedDto saveRecipe(RecipeSaveDto recipeSaveDto) {
        UserEntity userEntity = userRepository.findByEmail(recipeSaveDto.email()).orElseThrow(
                ()-> new UserNotFoundException(ErrorMessages.ENTITY_NOT_FOUND_MSG.formatted(recipeSaveDto.email()))
        );
        RecipeEntity recipeEntity= recipeRepository.findById(recipeSaveDto.recipeId()).orElseThrow(
                ()->new RecipeNotFoundException(ErrorMessages.RECIPE_NOT_FOUND.formatted(recipeSaveDto.recipeId()))
        );

        SavedRecipeEntity savedRecipeEntity = SavedRecipeEntity.builder()
                .recipe(recipeEntity)
                .user(userEntity)
                .time(recipeSaveDto.time())
                .build();
        savedRecipeRepostory.save(savedRecipeEntity);

        RecipeSavedDto recipeSavedDto = RecipeSavedDto.builder()
                .email(savedRecipeEntity.getUser().getEmail())
                .title(savedRecipeEntity.getRecipe().getTitle())
                .time(savedRecipeEntity.getTime())
                .build();
        return recipeSavedDto;
    }

}
