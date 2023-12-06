package com.spring.recipeapp.service;

import com.spring.recipeapp.controller.customResponse.PaginatedDisplayRecipeResponse;
import com.spring.recipeapp.dto.ingredient.IngredientDto;
import com.spring.recipeapp.dto.recipe.RecipeAddDto;
import com.spring.recipeapp.dto.recipe.RecipeDto;
import com.spring.recipeapp.dto.recipe.RecipeSpec;
import com.spring.recipeapp.dto.recipe.RecipeUpdateDto;
import com.spring.recipeapp.dto.step.StepDto;
import com.spring.recipeapp.entity.IngredientEntity;
import com.spring.recipeapp.entity.RecipeEntity;
import com.spring.recipeapp.entity.StepEntity;
import com.spring.recipeapp.entity.UserEntity;
import com.spring.recipeapp.exception.ErrorMessages;
import com.spring.recipeapp.exception.RecipeNotFoundException;
import com.spring.recipeapp.exception.UserNotFoundException;
import com.spring.recipeapp.mapper.IngredientMapper;
import com.spring.recipeapp.mapper.PaginatedDisplayResponseMapper;
import com.spring.recipeapp.mapper.RecipeMapper;
import com.spring.recipeapp.mapper.StepMapper;
import com.spring.recipeapp.repository.IngredientRepository;
import com.spring.recipeapp.repository.RecipeRepository;
import com.spring.recipeapp.repository.StepRepository;
import com.spring.recipeapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final StepRepository stepRepository;
    private final UserRepository userRepository;
    private final RecipeMapper recipeMapper;
    private final PaginatedDisplayResponseMapper paginatedDisplayResponseMapper;
    private final IngredientMapper ingredientMapper;
    private final StepMapper stepMapper;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository,
                         StepRepository stepRepository, UserRepository userRepository, RecipeMapper recipeMapper,
                         PaginatedDisplayResponseMapper paginatedDisplayResponseMapper, IngredientMapper ingredientMapper, StepMapper stepMapper) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.stepRepository = stepRepository;
        this.userRepository = userRepository;
        this.recipeMapper = recipeMapper;
        this.paginatedDisplayResponseMapper = paginatedDisplayResponseMapper;
        this.ingredientMapper = ingredientMapper;
        this.stepMapper = stepMapper;
    }

    public PaginatedDisplayRecipeResponse getFilteredDisplayRecipes(Double rating, String category,
                                                                    String title, Pageable pageable) {
        Specification<RecipeEntity> spec = RecipeSpec.filterBy(rating,category,title);
        return paginatedDisplayResponseMapper.toPaginatedDisplayRecipeResponse(recipeRepository.findAll(spec,pageable));
    }

    public RecipeDto addRecipe(RecipeAddDto recipeAddDto) {
        UserEntity user = userRepository.findByEmail(recipeAddDto.email()).orElseThrow(
                ()->new UserNotFoundException(ErrorMessages.ENTITY_NOT_FOUND_MSG.formatted((recipeAddDto.email())))
        );
        RecipeEntity recipeToSave = recipeMapper.addDtoToRecipeEntity(recipeAddDto);
        recipeToSave.setUser(user);
        RecipeEntity recipeSaved = recipeRepository.save(recipeToSave);
        List<IngredientEntity> ingredientEntityList = new ArrayList<>();
        for(IngredientDto ingredientDto: recipeAddDto.ingredients()){
            IngredientEntity ingredient =IngredientEntity.builder()
                    .name(ingredientDto.name())
                    .quantity(ingredientDto.quantity())
                    .unit(ingredientDto.unit())
                    .recipe(recipeSaved)
                    .build();
            ingredientEntityList.add(ingredient);
        }

        List<StepEntity> stepEntityList = new ArrayList<>();
        for(StepDto stepDto:recipeAddDto.steps()){
            StepEntity step = StepEntity.builder()
                    .number(stepDto.number())
                    .text(stepDto.text())
                    .recipe(recipeSaved)
                    .build();
            stepEntityList.add(step);
        }
        recipeSaved.setIngredients(ingredientEntityList);
        recipeSaved.setSteps(stepEntityList);
        return recipeMapper.toRecipeDto(recipeSaved);
    }

    public void deleteById(Long id) {
        recipeRepository.findById(id).orElseThrow(
                ()->new RecipeNotFoundException(ErrorMessages.RECIPE_NOT_FOUND.formatted(id))
        );
        recipeRepository.deleteById(id);
    }

    @Transactional
    public RecipeDto updateRecipe(RecipeUpdateDto recipeUpdateDto) {
        RecipeEntity recipeEntity= recipeRepository.findById(recipeUpdateDto.getId()).orElseThrow(
                ()->new RecipeNotFoundException(ErrorMessages.RECIPE_NOT_FOUND.formatted(recipeUpdateDto.getId()))
        );
        recipeEntity.setTitle(recipeUpdateDto.getTitle());
        recipeEntity.setCuisine(recipeUpdateDto.getCuisine());
        recipeEntity.setPhoto(recipeUpdateDto.getPhoto());
        recipeEntity.setCookTime(recipeUpdateDto.getCookTime());

        recipeEntity.getIngredients().clear();
        List<IngredientEntity> ingredientEntityList =ingredientMapper.toEntities(recipeUpdateDto.getIngredients());

        for(IngredientEntity ingredient:ingredientEntityList){
            ingredient.setRecipe(recipeEntity);
        }
        recipeEntity.getIngredients().addAll(ingredientEntityList);

        List<StepEntity> stepEntityList=stepMapper.toEntities(recipeUpdateDto.getSteps());
        recipeEntity.getSteps().clear();
        for(StepEntity step:stepEntityList){
            step.setRecipe(recipeEntity);
        }
        recipeEntity.getSteps().addAll(stepEntityList);

        return recipeMapper.toRecipeDto(recipeRepository.save(recipeEntity));
    }

    public RecipeDto getRecipeById(Long id) {
        RecipeEntity recipeEntity= recipeRepository.findById(id).orElseThrow(
                ()->new RecipeNotFoundException(ErrorMessages.RECIPE_NOT_FOUND.formatted(id))
        );
        return recipeMapper.toRecipeDto(recipeEntity);
    }
}
