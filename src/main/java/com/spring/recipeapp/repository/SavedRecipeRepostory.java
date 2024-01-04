package com.spring.recipeapp.repository;

import com.spring.recipeapp.dto.recipe.RecipeDisplayDto;
import com.spring.recipeapp.entity.SavedRecipeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavedRecipeRepostory extends JpaRepository<SavedRecipeEntity,Long> {
    Optional<SavedRecipeEntity> findByRecipe_IdAndUser_Email(Long recipeId,String email);

    @Query("SELECT " +
            "    new com.spring.recipeapp.dto.recipe.RecipeDisplayDto(" +
            "        r.id AS id, " +
            "        r.title AS title, " +
            "        r.cookTime as cookTime, " +
            "        COALESCE(AVG(COALESCE(re.rating, 0)), 0) as rating, " +
            "        r.photo AS photo, " +
            "        CASE WHEN sr.id IS NOT NULL THEN true ELSE false END AS isSaved" +
            "    ) " +
            "FROM " +
            "    RecipeEntity r " +
            "    LEFT JOIN r.reviews re " +
            "    JOIN r.user u " +
            "    LEFT JOIN r.savedByUsers sr ON r.id = sr.recipe.id AND sr.user.email = :currentUser " +
            "WHERE " +
            "    (:currentUser IS NULL OR sr.id IS NOT NULL) " +
            "GROUP BY " +
            "    r.id, r.cookTime, r.cuisine, r.photo, r.title, u.id, u.firstName, u.lastName, u.email, sr.id")
    Page<RecipeDisplayDto> findAllSavedRecipes(
            @Param("currentUser") String currentUser,
            Pageable pageable);

}
