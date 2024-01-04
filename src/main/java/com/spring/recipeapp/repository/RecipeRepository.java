package com.spring.recipeapp.repository;

import com.spring.recipeapp.dto.recipe.RecipeDisplayDto;
import com.spring.recipeapp.dto.recipe.RecipeWithStatusDto;
import com.spring.recipeapp.entity.RecipeEntity;
import com.spring.recipeapp.entity.ReviewEntity;
import com.spring.recipeapp.enums.Cuisine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<RecipeEntity,Long> {

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
            "    (:title IS NULL OR LOWER(r.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "    AND (:cuisine IS NULL OR r.cuisine = :cuisine) " +
            "    AND (:rating IS NULL OR " +
            "        (SELECT AVG(re.rating) FROM ReviewEntity re WHERE re.recipe.id = r.id) " +
            "            BETWEEN :rating - 0.5 AND :rating + 0.5) " +
            "    AND(:email IS NULL OR LOWER(r.user.email) = :email)" +
            "GROUP BY " +
            "    r.id, r.cookTime, r.cuisine, r.photo, r.title, u.id, u.firstName, u.lastName, u.email, sr.id")
    Page<RecipeDisplayDto> findFilteredRecipes(
            @Param("currentUser") String currentUser,
            @Param("email") String email,
            @Param("title") String title,
            @Param("cuisine") String cuisine,
            @Param("rating") Double rating,
            Pageable pageable);

    Optional<RecipeEntity>findById(Long id);

    @Query("SELECT " +
            "    NEW com.spring.recipeapp.dto.recipe.RecipeWithStatusDto(" +
            "        r AS recipeEntity, " +
            "        EXISTS (SELECT 1 FROM SavedRecipeEntity sr " +
            "                WHERE sr.user.email = :email AND sr.recipe = r) AS isSaved" +
            "    ) " +
            "FROM " +
            "    RecipeEntity r " +
            "WHERE " +
            "    r.id = :id")
    RecipeWithStatusDto getRecipeWithStatusById(@Param("id") Long id, @Param("email") String email);


    Page<RecipeEntity> findAll(Specification<RecipeEntity> recipesSavedByUser, Pageable pageable);

    @Query("SELECT r.reviews FROM RecipeEntity r WHERE r.id = :recipeId")
    Page<ReviewEntity> findReviewsByRecipeId(@Param("recipeId") Long recipeId, Pageable pageable);
}
