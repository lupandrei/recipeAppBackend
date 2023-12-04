package com.spring.recipeapp.controller;

import com.spring.recipeapp.controller.customResponse.PaginatedDisplayRecipeResponse;
import com.spring.recipeapp.dto.recipe.RecipeAddDto;
import com.spring.recipeapp.dto.recipe.RecipeDto;
import com.spring.recipeapp.dto.recipe.RecipeUpdateDto;
import com.spring.recipeapp.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/recipes")
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
    public ResponseEntity<RecipeDto>addRecipe(@RequestBody @Valid RecipeAddDto recipeAddDto){
        return new ResponseEntity<>(recipeService.addRecipe(recipeAddDto),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteRecipe(@PathVariable Long id){
        recipeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Long id, @RequestBody RecipeUpdateDto recipeUpdateDto){
        recipeUpdateDto.setId(id);
        return new ResponseEntity<>(recipeService.updateRecipe(recipeUpdateDto),HttpStatus.OK);
    }

}
