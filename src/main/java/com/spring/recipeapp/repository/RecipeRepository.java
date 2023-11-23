package com.spring.recipeapp.repository;

import com.spring.recipeapp.entity.RecipeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<RecipeEntity,Long> {
    Page<RecipeEntity> findAll(Specification<RecipeEntity> spec, Pageable pageable);
}
