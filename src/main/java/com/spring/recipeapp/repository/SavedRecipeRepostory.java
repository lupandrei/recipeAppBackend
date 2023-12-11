package com.spring.recipeapp.repository;

import com.spring.recipeapp.entity.SavedRecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedRecipeRepostory extends JpaRepository<SavedRecipeEntity,Long> {
}
