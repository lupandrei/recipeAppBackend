package com.spring.recipeapp.controller;


import com.spring.recipeapp.controller.customResponse.PaginatedDisplayRecipeResponse;
import com.spring.recipeapp.dto.recipe.RecipeSaveDto;
import com.spring.recipeapp.dto.recipe.RecipeSavedDto;
import com.spring.recipeapp.service.SavedRecipeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/save-recipe")
public class SavedRecipeController {
    private final SavedRecipeService savedRecipeService;

    public SavedRecipeController(SavedRecipeService savedRecipeService) {
        this.savedRecipeService = savedRecipeService;
    }

    @GetMapping()
    public ResponseEntity<PaginatedDisplayRecipeResponse> getSavedRecipes(@RequestParam String email,
                                                                          Pageable pageable){
        return new ResponseEntity<>(savedRecipeService.getSavedRecipes(email,pageable), HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<RecipeSavedDto> saveRecipe(@RequestBody @Valid RecipeSaveDto recipeSaveDto){
        return new ResponseEntity<>(savedRecipeService.saveRecipe(recipeSaveDto),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeSavedRecipe(@PathVariable Long id,Authentication authentication){
        savedRecipeService.removeSavedRecipe(id,authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
