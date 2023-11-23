package com.spring.recipeapp.repository;

import com.spring.recipeapp.entity.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<IngredientEntity,Long> {
}
