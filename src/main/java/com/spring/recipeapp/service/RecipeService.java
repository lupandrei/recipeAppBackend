package com.spring.recipeapp.service;

import com.spring.recipeapp.controller.customResponse.PaginatedDisplayRecipeResponse;
import com.spring.recipeapp.controller.customResponse.PaginatedDisplayReviewResponse;
import com.spring.recipeapp.dto.ingredient.IngredientDto;
import com.spring.recipeapp.dto.notification.RecipeAddNotification;
import com.spring.recipeapp.dto.recipe.RecipeAddDto;
import com.spring.recipeapp.dto.recipe.RecipeAddedDto;
import com.spring.recipeapp.dto.recipe.RecipeDto;
import com.spring.recipeapp.dto.recipe.RecipeUpdateDto;
import com.spring.recipeapp.dto.recipe.RecipeWithStatusDto;
import com.spring.recipeapp.dto.review.ReviewAddDto;
import com.spring.recipeapp.dto.step.StepDto;
import com.spring.recipeapp.entity.IngredientEntity;
import com.spring.recipeapp.entity.RecipeEntity;
import com.spring.recipeapp.entity.ReviewEntity;
import com.spring.recipeapp.entity.StepEntity;
import com.spring.recipeapp.entity.UserEntity;
import com.spring.recipeapp.enums.Cuisine;
import com.spring.recipeapp.exception.ErrorMessages;
import com.spring.recipeapp.exception.RecipeNotFoundException;
import com.spring.recipeapp.exception.UserNotFoundException;
import com.spring.recipeapp.mapper.IngredientMapper;
import com.spring.recipeapp.mapper.PaginatedDisplayResponseMapper;
import com.spring.recipeapp.mapper.PaginatedDisplayReviewResponseMapper;
import com.spring.recipeapp.mapper.RecipeMapper;
import com.spring.recipeapp.mapper.ReviewMapper;
import com.spring.recipeapp.mapper.StepMapper;
import com.spring.recipeapp.repository.RecipeRepository;
import com.spring.recipeapp.repository.ReviewRepository;
import com.spring.recipeapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {

    private final NotificationService notificationService;
    private final RecipeRepository recipeRepository;

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RecipeMapper recipeMapper;
    private final PaginatedDisplayResponseMapper paginatedDisplayResponseMapper;
    private final IngredientMapper ingredientMapper;
    private final StepMapper stepMapper;

    private final ReviewMapper reviewMapper;
    private final PaginatedDisplayReviewResponseMapper paginatedDisplayReviewResponseMapper;

    @Autowired
    public RecipeService(NotificationService notificationService, RecipeRepository recipeRepository, ReviewRepository reviewRepository,
                         UserRepository userRepository, RecipeMapper recipeMapper,
                         PaginatedDisplayResponseMapper paginatedDisplayResponseMapper, IngredientMapper ingredientMapper,
                         StepMapper stepMapper, ReviewMapper reviewMapper,
                         PaginatedDisplayReviewResponseMapper paginatedDisplayReviewResponseMapper) {
        this.notificationService = notificationService;
        this.recipeRepository = recipeRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.recipeMapper = recipeMapper;
        this.paginatedDisplayResponseMapper = paginatedDisplayResponseMapper;
        this.ingredientMapper = ingredientMapper;
        this.stepMapper = stepMapper;
        this.reviewMapper = reviewMapper;
        this.paginatedDisplayReviewResponseMapper = paginatedDisplayReviewResponseMapper;
    }

    public PaginatedDisplayRecipeResponse getFilteredDisplayRecipes(Double rating, String category,
                                                                    String title, String email, Boolean isSaved,
                                                                    String currentUser, Pageable pageable) {
        Cuisine cuisine;
        if(category!=null)
             cuisine = Cuisine.valueOf(category);
        else
            cuisine=null;
        return paginatedDisplayResponseMapper.fromRecipeDisplayDtotoPaginatedDisplayResponse(recipeRepository
                .findFilteredRecipes(currentUser,email,title,cuisine ,rating,pageable));
    }

    public RecipeAddedDto addRecipe(RecipeAddDto recipeAddDto) {
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
        RecipeAddNotification recipeAddNotification = RecipeAddNotification.builder()
                .id(recipeSaved.getId())
                .title(recipeSaved.getTitle())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
        notificationService.notifyFollowers(recipeAddDto.email(),"/topic/recipes",recipeAddNotification);
        return recipeMapper.toRecipeAddedDto(recipeSaved);
    }

    public void deleteById(Long id) {
        recipeRepository.findById(id).orElseThrow(
                ()->new RecipeNotFoundException(ErrorMessages.RECIPE_NOT_FOUND.formatted(id))
        );
        recipeRepository.deleteById(id);
    }

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

    public RecipeDto getRecipeWithStatusById(Long id,String email){
        recipeRepository.findById(id).orElseThrow(
                ()->new RecipeNotFoundException(ErrorMessages.RECIPE_NOT_FOUND.formatted(id))
        );
        RecipeWithStatusDto recipeEntity= recipeRepository.getRecipeWithStatusById(id,email);
        RecipeDto recipeDto= recipeMapper.recipeWithStatusToRecipeDto(recipeEntity);
        recipeDto.getUserRecipeDisplayInformationDto().setIsFollowing(userRepository
                .isFollowing(email,recipeDto.getUserRecipeDisplayInformationDto().getEmail()));
        return recipeDto;
    }

    public PaginatedDisplayReviewResponse getRecipeReviews(Long id, Pageable pageable) {
        recipeRepository.findById(id).orElseThrow(
                ()->new RecipeNotFoundException(ErrorMessages.RECIPE_NOT_FOUND.formatted(id))
        );
        return paginatedDisplayReviewResponseMapper.toPaginatedReviewResponse(
                reviewMapper.toReviewsDto(recipeRepository.findReviewsByRecipeId(id,pageable))
        );
    }

    public ReviewAddDto addRecipeReview(ReviewAddDto reviewAddDto){
        RecipeEntity recipeEntity=recipeRepository.findById(reviewAddDto.getId()).orElseThrow(
                ()->new RecipeNotFoundException(ErrorMessages.RECIPE_NOT_FOUND.formatted(reviewAddDto.getId()))
        );
        UserEntity userEntity =userRepository.findByEmail(reviewAddDto.getEmail()).orElseThrow(
                ()->new UserNotFoundException(ErrorMessages.ENTITY_NOT_FOUND_MSG.formatted(reviewAddDto.getEmail()))
        );
        ReviewEntity reviewEntity = ReviewEntity.builder()
                .reviewText(reviewAddDto.getReviewText())
                .rating(reviewAddDto.getRating())
                .recipe(recipeEntity)
                .user(userEntity)
                .time(reviewAddDto.getTime())
                .build();
        reviewRepository.save(reviewEntity);
        return reviewAddDto;
    }
}
