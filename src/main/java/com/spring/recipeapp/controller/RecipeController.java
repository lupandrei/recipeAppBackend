package com.spring.recipeapp.controller;

import com.spring.recipeapp.controller.customResponse.PaginatedDisplayRecipeResponse;
import com.spring.recipeapp.dto.recipe.RecipeAddDto;
import com.spring.recipeapp.dto.recipe.RecipeDto;
import com.spring.recipeapp.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private final  RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/display")
    public ResponseEntity<PaginatedDisplayRecipeResponse>getFilteredDisplayRecipes(
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String title,
            Pageable pageable
    ){
        return new ResponseEntity<>(recipeService.getFilteredDisplayRecipes(rating,category,title,pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RecipeDto>addRecipe(@RequestBody RecipeAddDto recipeAddDto){
        return new ResponseEntity<>(recipeService.addRecipe(recipeAddDto),HttpStatus.CREATED);
    }

}
